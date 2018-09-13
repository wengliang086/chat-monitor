#!/bin/bash

remote_deploy_home=/home/fastsdk/deploy_sdk
local_deploy_home=/data/sharedata/access/sdk
#local_deploy_home=/data/sharedata/access

check(){
  if [ $? -ne 0 ];then
   echo "===================$1,执行失败======================="
   exit 1;
  fi
   echo "===================$1,执行成功========================"
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

if [ "" == "$1" ] ; then
	echo "在命令后面加一个渠道 或者 all"
	for ((i=0; i<${#allHoolaiChannels[@]}; i++))
	do
		paramsarray=(${allHoolaiChannels[$i]})
		echo "${paramsarray[0]}"
	done
    exit 1;
else
	if [ "all" = "$1" ] ; then
		for ((i=0; i<${#allHoolaiChannels[@]}; i++))
		do
			paramsarray=(${allHoolaiChannels[$i]})
			targetApkName=(${paramsarray[1]})
			cp $remote_deploy_home/$targetApkName".apk" $local_deploy_home
		done
    else
		checkParas $1
		echo "targetApkName_global=$targetApkName_global"
		cp $remote_deploy_home/$targetApkName_global".apk" $local_deploy_home
    fi
fi

check "部署"




