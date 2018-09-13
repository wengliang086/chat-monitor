#!/bin/bash

local_deploy_home=/home/fastsdk/deploy_sdk
sdk_home=/data/bin/conf/sdk
source $WORKSPACE_HOME/runtime/bin/env.sh
source $WORKSPACE_HOME/runtime/bin/util.sh

if [ "" == "$1" ] ; then
    echo "在命令后面加一个渠道，如uc,如果打包全部,请输入all."
    exit 1;
else
    if [ "all" == "$1" ] ; then
        cp -R $local_deploy_home/* $sdk_home/
        check "部署$sdk_home/目录成功"
        #rm -rf $local_deploy_home/*
        #check "清空部署目录$local_deploy_home/"
    elif  [ "$1" = "assets" ] || [ "$1" = "manifest" ] || [ "$1" = "smali" ] || [ "$1" = "application" ] || [ "$1" = "res" ] ;then	
        if [ ! -d "$local_deploy_home/$1" ];then
            echo "没有可部署的文件$local_deploy_home/$1"
            exit 0
        fi
        if [ ! -f "$local_deploy_home/$1.zip" ];then
            echo "没有可部署的文件$local_deploy_home/$1.zip"
            exit 0
        fi
      
	cp  -R  $local_deploy_home/$1     $sdk_home/
        check "部署$local_deploy_home/$1到$sdk_home/$1成功"
        cp  -R  $local_deploy_home/$1.zip $sdk_home/
        check "部署$local_deploy_home/$1.zip到$sdk_home/$1.zip文件成功"
        #rm -rf $local_deploy_home/$1
        #check "删除部署目录$local_deploy_home/$1"
        #rm -rf $local_deploy_home/$1.zip
        #check "删除部署文件$local_deploy_home/$1.zip"
    else
        if [ ! -f "$local_deploy_home/$1_fastaccess_sdk.apk" ];then
            echo "没有可部署的文件$local_deploy_home/$1_fastaccess_sdk.apk"
            exit 0
        fi
        cp  -R  $local_deploy_home/$1_fastaccess_sdk.apk $sdk_home/
        check "部署$local_deploy_home/$1_fastaccess_sdk.apk到$sdk_home/目录成功"
        #rm -rf $local_deploy_home/$1_fastaccess_sdk.apk
        #check "删除部署文件$local_deploy_home/$1_fastaccess_sdk.apk"
    fi
fi
