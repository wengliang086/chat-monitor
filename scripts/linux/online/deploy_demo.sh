#!/bin/bash

remote_deploy_home=/home/fastsdk/deploy
local_deploy_home=/data/sharedata/access/download/demo

check(){
  if [ $? -ne 0 ];then
   echo "===================$1,执行失败======================="
   exit 1;
  fi
   echo "===================$1,执行成功========================"
}

if [ "" == "$1" ] ; then
    echo "在命令后面加一个渠道，如hoolai_sdk_demo,如果打包全部,请输入all."
    exit 1;
else
	if [ "all" == "$1" ] ; then
		cp -R $remote_deploy_home/hoolai_*emo*.zip $local_deploy_home
		cp -R $remote_deploy_home/AccessSDK_iOS.zip $local_deploy_home
	else
		cp -R $remote_deploy_home/$1.zip $local_deploy_home
	fi
fi

check "成功复制文件到本机"




