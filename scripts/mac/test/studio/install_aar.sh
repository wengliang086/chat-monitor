#!/bin/sh

JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_91.jdk/Contents/Home
echo $JAVA_HOME

sdk_build_dir=$WORKSPACE_HOME/build/fast_access_sdk_dir_studio
svn=http://svn.hoolai.com/access-platform/trunk/fast_access_sdk_AndroidStudio

check(){
  if [ $? -ne 0 ];then
   echo "===================$1,执行失败======================="
   exit 1;
  fi
   echo "===================$1,执行成功========================"
}

updateAndInstall(){
	if [ -d $sdk_build_dir/$1 ];then
		cd $sdk_build_dir/$1
		rm -f ./app/build.gradle
		rm -f gradle.properties
		svn revert $sdk_build_dir/$1
		svn update $sdk_build_dir/$1
		check "$1 SVN 更新"
	else
		svn co $svn/$1 -r head $sdk_build_dir/$1
		check "$1 SVN 检出"
	fi
	
	rm -f local.properties
	sh gradlew clean
	sh gradlew install
	check "$1 install"
}

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
			updateAndInstall $hoolaiChannel
		done
    else
		checkParas $1
        updateAndInstall $1
    fi
fi

