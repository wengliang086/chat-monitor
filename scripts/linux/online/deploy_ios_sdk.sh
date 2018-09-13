#!/bin/sh

local_deploy_home=/home/fastsdk/deploy_ios_sdk
sdk_home=/data/sharedata/access/sdk
source $WORKSPACE_HOME/runtime/bin/env.sh
source $WORKSPACE_HOME/runtime/bin/util.sh
source $WORKSPACE_HOME/runtime/bin/ios_env.sh

if [ "" == "$1" ] ; then
    echo "在命令后面加一个渠道，如itools,如果打包全部,请输入all."
    exit 1;
else
    if [ "all" == "$1" ] ; then
        cp -r $local_deploy_home/* $sdk_home/
        check "部署$sdk_home/目录成功"
        rm -rf $local_deploy_home/*
        check "清空部署目录$local_deploy_home/"
    else
	check_channel $1
	if [ $? -ne 0 ];then
            echo "渠道$1输入错误，请重新输入"
            exit 1;
	fi
	
	if [ ! -f "$local_deploy_home/$1_ios_sdk.zip" ];then
            echo "没有可部署的文件$local_deploy_home/$1_ios_sdk.zip"
            exit 0
        fi

        cp  -r  $local_deploy_home/$1_ios_sdk.zip $sdk_home/
        check "部署$local_deploy_home/$1_ios_zip.zip到$sdk_home/$1_ios_sdk.zip文件成功"
        rm -rf $local_deploy_home/$1_ios_sdk.zip
        check "删除部署文件$local_deploy_home/$1_ios_sdk.zip"
    fi
fi
