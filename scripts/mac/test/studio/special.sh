#!/bin/sh

JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_91.jdk/Contents/Home
echo $JAVA_HOME

sdk_build_dir=$WORKSPACE_HOME/build/fast_access_sdk_dir_studio
sdk_deploy_dir=$WORKSPACE_HOME/deploy/sdk

svn=http://svn.hoolai.com/access-platform/trunk/fast_access_sdk_AndroidStudio

check(){
  if [ $? -ne 0 ];then
   echo "===================$1,执行失败======================="
   exit 1;
  fi
   echo "===================$1,执行成功========================"
}

updateAndInstall(){
	echo "开始部署$1 目标文件$2"
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
	
	cd $sdk_build_dir/$1
	rm -rf local.properties
	sed -i "" "s/PROJ_NAME=fastaccess_sdk/PROJ_NAME=app/g" gradle.properties
	sed -i "" "s/com.android.library/com.android.application/g" ./app/build.gradle
	sh gradlew clean
	sh gradlew assembleRelease
	check "$1 assembleRelease"
	
	apkPath=$sdk_build_dir/$1/app/build/outputs/apk/release/app-release-unsigned.apk
	targetPath=$sdk_deploy_dir/$2.apk
	
	svn_version=`svn info http://svn.hoolai.com/access-platform/trunk/fast_access_sdk_AndroidStudio/fastaccess_sdk | grep "Last Changed Rev:" | awk '{print $4}'`
	echo "svn_version=$svn_version"
	
	rsync -avr $apkPath $targetPath
	rsync -avr $apkPath $targetPath"_svn"$svn_version
	check "本地部署"
	
	uploadOnline $targetPath
	uploadOnline $targetPath"_svn"$svn_version
	check "$targetPath 上传"
}

allHoolaiChannels=("fastaccess_sdk fastaccess_sdk-release-unsigned" "support-v4 support-v4")

targetApkName_global="undefined"

checkParas(){
	tag="false"
	for((k=0;k<${#allHoolaiChannels[@]};k++))
		do
			paramsarray=(${allHoolaiChannels[k]})
			if [ ${paramsarray[0]} = $1 ]; then
				tag="true"
				targetApkName_global=${paramsarray[1]}
				break
			fi
		done
	if [ "$tag" = "false" ] ; then
		echo "输入参数$1不合法"
		exit 1;
	fi
}

uploadOnline(){
	rsync -avzr --progress --exclude '.svn' -e'ssh -p 22' $1  fastsdk@119.29.21.153:/home/fastsdk/deploy_sdk
}

if [ "" == "$1" ] ; then
    echo "在命令后面加一个渠道 或者 all"
	for (( i=0; i<${#allHoolaiChannels[@]}; i++  ))
	do
		paramsarray=(${allHoolaiChannels[$i]})
		echo "${paramsarray[0]}"
	done
    exit 1;
else
    if [ "all" = "$1" ] ; then
		for (( i=0; i<${#allHoolaiChannels[@]}; i++  ))
		do
			paramsarray=(${allHoolaiChannels[$i]})
			projectName=(${paramsarray[0]})
			targetApkName=(${paramsarray[1]})
			updateAndInstall $projectName $targetApkName
		done
    else
		checkParas $1
        updateAndInstall $1 $targetApkName_global
    fi
fi

