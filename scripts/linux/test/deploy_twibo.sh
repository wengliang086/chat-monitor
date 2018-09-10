#!/bin/sh

check(){
    if [ $? -ne 0 ];then
        echo "===================$1,执行失败======================="
        exit 1;
    fi    
    echo "===================$1,执行成功========================"
}

rsync -avzr --progress --delete --exclude '.svn': -e'ssh -p 22'   /root/build/community/twibo/public_release/  fastsdk@119.29.21.153:/home/fastsdk/deploy/twibo/
check "上传到正式环境"


