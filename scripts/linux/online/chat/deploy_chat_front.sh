#!/bin/bash

deploy_dir=/home/fastsdk/deploy/chat-moniter
target_dir=/usr/local/services/nginx
source util.sh

rm -rf $deploy_dir/chat-moniter
cd $deploy_dir
unzip chat-admin.zip

#rm -rf $target_dir/chat-moniter
cp -r $deploy_dir/chat-admin  $target_dir
check "chat-admin 部署"
