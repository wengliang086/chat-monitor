#!/bin/bash

source $WORKSPACE_HOME/runtime/bin/util.sh

if [ ! "$1" ]; then
	echo "没有输入回滚的文件名"
	exit 1
fi

nginx_dir=/usr/local/services/nginx
deploy_dir=/data/app_bak/nginx/$1
nginx_bak=/data/app_bak/nginx

if [ ! -d "$deploy_dir" ]; then
	echo "没有可回滚的目录$deploy_dir"
	exit 1
fi

#将文件复制到nginx/deploy目录下
check_and_create $nginx_dir/deploy
rm -rf $nginx_dir/deploy/*
cp -r $deploy_dir/* $nginx_dir/deploy/

#测试nginx.conf
$nginx_dir/sbin/nginx -c deploy/nginx.conf -t
check "检查部署文件"

#部署nginx.conf
cp -rfv $nginx_dir/deploy/* $nginx_dir/conf/
check "部署文件"

#重启nginx
$nginx_dir/sbin/nginx -t
check "配置文件检查"
$nginx_dir/sbin/nginx -s reload
check "重启nginx"
