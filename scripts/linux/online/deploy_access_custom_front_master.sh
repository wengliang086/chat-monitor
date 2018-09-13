#!/bin/bash
source $WORKSPACE_HOME/runtime/bin/util.sh

deploy_file=/home/fastsdk/deploy/access_custom_front
local_deploy_path=/usr/local/services/nginx/access_custom_front

rm -rf /usr/local/services/nginx/access_custom_front/*

cp -R $deploy_file/* $local_deploy_path/ 

check "成功复制文件到本机"




