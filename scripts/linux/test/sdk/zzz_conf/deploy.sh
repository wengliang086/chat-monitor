#!/bin/bash

#sdk_deploy_dir=/root/access/fastaccess_web/sdk
cd `dirname $0`
cd ..
check(){
  if [ $? -ne 0 ];then
   echo "===================$1,执行失败======================="
   exit 1;
  fi
   echo "===================$1,执行成功========================"
}

deploy(){
  if [ "$1" = "assets" ] || [ "$1" = "manifest" ] || [ "$1" = "smali" ] || [ "$1" = "application" ] || [ "$1" = "res" ] ;then
	rm -rf $sdk_deploy_dir/$1
	rsync -avr --exclude '.svn' $1 $sdk_deploy_dir/
	check "$1部署"
	rm -rf $sdk_deploy_dir/$1.zip
	cd $sdk_deploy_dir
    zip -r $1.zip $1
  else
	echo "当前路径=====$1_fastaccess_sdk"
    cd $1_fastaccess_sdk
  	if [ ! -f "sdk_version.txt" ]; then
		echo "=================== 版本文件不存在 ==================="
		exit 1;
	fi
	if [ ! -d "assets" ]; then 
		mkdir "assets";
		echo "=================== 创建assets目录 ==================="
	fi
	dateStr=`date`
	echo $dateStr
	sed "1i\部署日期：$dateStr" sdk_version.txt > "assets/sdk_version.txt"
  
	ant -Dfastaccess_sdk_dir=/root/fastaccess/common_modules/android-sdk-linux -Dprofile=test -f build.xml release
	check "$1打包"
	rm -rf $sdk_deploy_dir/$1_fastaccess_sdk.apk
	rsync -avr bin/$1_fastaccess_sdk-release-unsigned.apk $sdk_deploy_dir/$1_fastaccess_sdk.apk
	check "$1部署"
	
	cd `dirname $0`
	cd ..
	basepath=$(cd `dirname $0`; pwd)
	
	sh $basepath/muti_version_sdk.sh $1_fastaccess_sdk/sdk_version.txt $sdk_deploy_dir/$1_fastaccess_sdk.apk $1 $2
	check "$1多版本"
  fi
}

deploy $@
