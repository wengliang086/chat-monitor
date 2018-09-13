#!/bin/bash


source $WORKSPACE_HOME/runtime/bin/util.sh

nginx_dir=/usr/local/services/nginx
deploy_zip=/home/fastsdk/deploy/nginx_conf.zip
nginx_bak=/data/app_bak/nginx

if [ ! -e "$deploy_zip"  ];then
 echo "没有可部署的文件$deploy_zip"
 exit 1
fi


#将文件复制到nginx/deploy目录下
check_and_create $nginx_dir/deploy
rm -rf $nginx_dir/deploy/*
echo "unzip -o  $deploy_zip $nginx_dir/deploy/"
unzip -o  $deploy_zip -d $nginx_dir/deploy/

#测试nginx.conf
$nginx_dir/sbin/nginx -c deploy/nginx.conf -t
check "将要部署的配置文件检查"

#备份nginx.conf
bak_dir=nginx_conf.`date +%Y_%m_%d_%H_%M_%S`
check_and_create $nginx_bak/$bak_dir


\cp -rfv  $nginx_dir/conf/nginx.conf $nginx_bak/$bak_dir/
if [ -d "$nginx_dir/conf/app_proxy_pass" ];then
\cp -rfv  $nginx_dir/conf/app_proxy_pass $nginx_bak/$bak_dir/app_proxy_pass
fi
echo "备份目录$bak_dir"

#部署nginx.conf
\cp -rfv $nginx_dir/deploy/* $nginx_dir/conf/
check "部署nginx配置文件"

#重启nginx
$nginx_dir/sbin/nginx -t
check "配置文件检查"
$nginx_dir/sbin/nginx -s quit
$nginx_dir/sbin/nginx
check "重启nginx"

rm -rfv $deploy_zip 
check "删除部署文件"

