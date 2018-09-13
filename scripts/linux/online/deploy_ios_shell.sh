#!/bin/bash

local_deploy_home=/home/fastsdk/ios_shell
shell_home=/data/sharedata/access/ios_package_tools
source $WORKSPACE_HOME/runtime/bin/env.sh
source $WORKSPACE_HOME/runtime/bin/util.sh
source $WORKSPACE_HOME/runtime/bin/ios_env.sh


if [ -d "$shell_home" ];then
	echo "=======清空$shell_home目录"
	rm -f $shell_home/*
else
	echo "=======创建$shell_home目录"
	mkdir $shell_home
fi

echo "======开始部署ios打包shell========"
cp -r $local_deploy_home/* $shell_home
##rsync -a --delete $local_deploy_home/ $shell_home
echo "======部署IOS shell完成==========="

