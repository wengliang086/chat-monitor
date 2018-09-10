#! /bin/bash

pid=$(lsof -i:4000|grep hexo|tail -1|awk '"$1"!=""{print $2}')
if [ -z $pid ]
then
    echo "No process to be killed"
else
    kill -9 $pid
    echo "Process pid=$pid has been killed"
fi

cd /root/build/hexo_admin/
nohup hexo server -d > docs.log &
