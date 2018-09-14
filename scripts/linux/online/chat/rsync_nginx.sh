#!/bin/bash

#参数（1、远程服务器IP）

# 软连接 （/usr/local/services/nginx -> /data/nginx）
rsync -avzr --progress --delete --exclude 'proxy_temp': -e'ssh -p 22' /data/nginx/  root@$1:/data/nginx/ 
ssh root@$1 "/usr/local/services/nginx/sbin/nginx -s reload"
#ssh root@$1 "/usr/local/services/nginx/sbin/nginx -s quit"
#sleep 2
#ssh root@$1 "/usr/local/services/nginx/sbin/nginx"
