#!/bin/bash

#参数（1、远程服务器IP）

# 软连接 （/usr/local/services/nginx -> /data/nginx）
rsync -avzr --progress --delete --exclude 'proxy_temp': -e'ssh -p 22' /data/nginx/  root@$1:/data/nginx/
# 简单的远程命令执行方式（必须用双引号，如果执行多条指令，用分号分割）
ssh root@$1 "/usr/local/services/nginx/sbin/nginx -s reload"
#ssh root@$1 "/usr/local/services/nginx/sbin/nginx -s quit"
#sleep 2
#ssh root@$1 "/usr/local/services/nginx/sbin/nginx"
