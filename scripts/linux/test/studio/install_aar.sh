#!/bin/sh

JAVA_HOME=/root/workspace/common/jdk1.8.0_102
echo $JAVA_HOME

sdk_build_dir=/root/build/fast_access_sdk_dir_studio
svn=http://svn.hoolai.com/access-platform/trunk/fast_access_sdk_AndroidStudio

#if [ $# -lt 1 ];then
#	echo "主干部署"
#else
#	echo "分支$1部署"
#	sdk_build_dir=/root/build/fast_access_sdk_dir_studio/$1
#	svn=http://svn.hoolai.com/access-platform/branches/fast_access_sdk_AndroidStudio/$1
#fi

check(){
  if [ $? -ne 0 ];then
   echo "===================$1,执行失败======================="
   exit 1;
  fi
   echo "===================$1,执行成功========================"
}

updateAndInstall(){
	svn co $svn/$1 -r head $sdk_build_dir/$1
	check "$1 SVN 检出"
	
	cd $sdk_build_dir/$1
	rm -rf local.properties
	#sed -i "s/22.0.1/22.0.0/g" ./$2/build.gradle
	sh gradlew clean
	sh gradlew install
	check "$1 install"
}

beforeUpdateAndInstall(){
	echo "开始部署$1"
	if [[ $1 = "fastaccess_sdk" ]] ; then
		updateAndInstall $1 fastaccess_sdk
	else
		updateAndInstall $1 app
	fi
}

#updateAndInstall fastaccess_sdk fastaccess_sdk
#updateAndInstall bdwallet_pay_sdk app
#updateAndInstall wepayLib app
#updateAndInstall hoolai_sdk_pay app


#allHoolaiChannels=("fastaccess_sdk" "bdwallet_pay_sdk" "wepayLib" "hoolai_sdk_common" "hoolai_sdk_account" "hoolai_sdk_pay" "hoolai_pay_mm" "hoolai_pay_weixin") 
allHoolaiChannels=("fastaccess_sdk" "hoolai_sdk_common" "hoolai_sdk_account" "hoolai_sdk_pay")

checkParas(){
	tag="false"
	for((k=0;k<${#allHoolaiChannels[@]};k++))
		do
			if [ ${allHoolaiChannels[k]} = $1 ]; then
				tag="true"
				break
			fi
		done
	if [ "$tag" = "false" ] ; then
		echo "输入参数$1不合法"
		exit 1;
	fi
}

if [ "" == "$1" ] ; then
    echo "在命令后面加一个渠道 或者 all（注意，只包含hoolai相关渠道）"
	for (( i=0; i<${#allHoolaiChannels[@]}; i++  ))
	do
		echo "${allHoolaiChannels[$i]}"
	done
    exit 1;
else
    if [ "all" = "$1" ] ; then
		for (( i=0; i<${#allHoolaiChannels[@]}; i++  ))
		do
			hoolaiChannel=(${allHoolaiChannels[$i]})
			beforeUpdateAndInstall $hoolaiChannel
		done
    else
		checkParas $1
        beforeUpdateAndInstall $1
    fi
fi

