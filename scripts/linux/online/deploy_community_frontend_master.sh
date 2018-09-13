#!/bin/bash
source $WORKSPACE_HOME/runtime/bin/util.sh

deploy_file=/home/fastsdk/deploy/twibo
local_deploy_path=/usr/local/services/nginx/community_frontend

rm -rf /usr/local/services/nginx/community_frontend/*

cp -R $deploy_file/* $local_deploy_path/ 

check "成功复制文件到本机"




