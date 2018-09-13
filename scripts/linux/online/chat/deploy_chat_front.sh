#!/bin/bash

deploy_dir=/home/fastsdk/deploy/chat-moniter
target_dir=/usr/local/services/nginx
source ./util.sh

rm -rf $deploy_dir/chat-admin
cd $deploy_dir
unzip chat-admin.zip

#rm -rf $target_dir/chat-admin
cp -r $deploy_dir/chat-admin  $target_dir
check "chat-admin 部署"
