#!/bin/sh

current_dir=`pwd`
cd /usr/local/services/nginx
rm -rf /root/nginx_conf_bak/nginx_conf.tar.gz
tar -zcvf /root/nginx_conf_bak/nginx_conf.tar.gz conf/
cd $current_dir

