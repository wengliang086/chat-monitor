#!/bin/sh

check(){
    if [ $? -ne 0 ];then
        echo "===================$1,执行失败======================="
        exit 1;
    fi    
    echo "===================$1,执行成功========================"
}

nginx_conf_dir=/Users/access/workspace/build/nginx_conf
rm -rf $nginx_conf_dir
rm -rf $nginx_conf_dir.zip


svn co http://svn.hoolai.com/access-platform/trunk/fast_access/nginx_conf $nginx_conf_dir
check "检出SVN"

find $nginx_conf_dir -type d -name ".svn"|xargs rm -rf

current_dir=`pwd`
cd $nginx_conf_dir

zip -r ../nginx_conf.zip *

cd $current_dir

scp  -r /Users/access/workspace/build/nginx_conf.zip  fastsdk@119.29.21.153:/home/fastsdk/deploy/
check "上传到正式环境"



