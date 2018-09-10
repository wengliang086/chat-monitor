#!bin/bash

js_resource_dir=/root/access/fastaccess_web/ios_js
remote_deploy_dir=/home/fastsdk/deploy_ios_js

source $WORKSPACE_HOME/runtime/bin/env_ios.sh

check(){
     if [ $? -ne 0 ];then
      echo "===================$1,执行失败======================="
      exit 1;
     fi
      echo "===================$1,执行成功========================"
 }

check_channel(){
    if [ "$1" = "all" ];then
        return 0
    else
        for ((i=0; i<${#CHANNEL_ARRAY[@]}; i++ ));
        do
            attr=(${CHANNEL_ARRAY[$i]})
            if [ "$1" = "${attr[0]}" ];then
                return 0
            fi
        done
    fi
    return 1
}

channel=$1

if [ ! $channel ] || [ "$channel" = "" ];then
	echo "请输入渠道参数！"
	exit 1;
fi

echo "==========检查渠道参数 $channel=========="
check_channel $channel
if [ $? -ne 0 ];then
	echo "渠道$channel输入错误，请重新输入"
	exit 1;
fi

echo "==========渠道参数输入正确=========="

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

ssh -p 22 fastsdk@119.29.21.153 "mkdir -p $remote_deploy_dir/$relative_path"

rsync -avr $js_resource_dir/$relative_path/* fastsdk@119.29.21.153:$remote_deploy_dir/$relative_path
check "同步Ios js"
