#!/bin/bash 
js_resource_dir=/home/fastsdk/deploy_ios_js
remote_deploy_dir=/data/sharedata/access/ios_js

check(){
     if [ $? -ne 0 ];then
      echo "===================$1,执行失败======================="
      exit 1;
     fi
      echo "===================$1,执行成功========================"
 }

channel=$1

if [ ! $channel ] || [ "$channel" = "" ];then
	echo "请输入渠道参数！"
	exit 1;
fi

relative_path=$channel
version=$2
if [ $channel = "hoolai" ];then
    if [ ! $version ] || [ "$version" = "" ];then
	echo "hoolai请输入版本号重试！！！"
    	exit 1;
    fi
    relative_path=$relative_path/$version
fi


if [ ! -d $js_resource_dir/$relative_path ];then
	echo "部署版本不存在！！！"
	exit 1;
fi

if [ ! -d $remote_deploy_dir/$relative_path ];then
	mkdir -p $remote_deploy_dir/$relative_path
fi

rsync -avr $js_resource_dir/$relative_path/* $remote_deploy_dir/$relative_path
check "同步Ios js"

rm -rf $js_resource_dir/$relative_path
check "删除预部署文件版本<$js_resource_dir/$relative_path>"
