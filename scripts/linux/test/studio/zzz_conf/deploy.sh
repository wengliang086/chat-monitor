#!/bin/bash

#sdk_deploy_dir=/root/access/fastaccess_web/sdk

JAVA_HOME=/root/workspace/common/jdk1.8.0_102
echo $JAVA_HOME

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
	#ant -Dfastaccess_sdk_dir=/root/fastaccess/common_modules/android-sdk-linux -Dprofile=test -f $1_fastaccess_sdk/build.xml release
	cd $sdk_build_dir/$1_fastaccess_sdk
	rm -rf local.properties
	sed -i "s/22.0.0/22.0.1/g" ./app/build.gradle
	echo "=================== $1 打包 ==================="
	if [ ! -f "sdk_version.txt" ]; then
		echo "=================== 版本文件不存在 ==================="
		exit 1;
	fi
	if [ ! -d "app/src/main/assets" ]; then 
		mkdir "app/src/main/assets";
		echo "=================== 创建assets目录 ==================="
	fi
	dateStr=`date`
	echo $dateStr
	sed "1i\部署日期：$dateStr" sdk_version.txt > "app/src/main/assets/sdk_version.txt"
	#gradle build
	sh gradlew build
	check "$1打包"
	rm -rf $sdk_deploy_dir/$1_fastaccess_sdk.apk
	rsync -avr ./app/build/outputs/apk/app-release-unsigned.apk $sdk_deploy_dir/$1_fastaccess_sdk.apk
	check "$1部署"
	
	basepath=$(cd `dirname $0`; pwd)
	
	echo "模式$2"
	
	sh $basepath/muti_version_sdk.sh $sdk_build_dir/$1_fastaccess_sdk/sdk_version.txt $sdk_deploy_dir/$1_fastaccess_sdk.apk $1 $2
	
  fi
}

deploy $@
