#!/bin/bash
source /data/bin/common_env.sh

rsync -avzr --progress --delete --exclude 'proxy_temp': -e'ssh -p 22' /data/nginx/  root@$1:/data/nginx/ 
ssh root@$1 "/usr/local/services/nginx/sbin/nginx -s reload"
#ssh root@$1 "/usr/local/services/nginx/sbin/nginx -s quit"
#sleep 2
#ssh root@$1 "/usr/local/services/nginx/sbin/nginx"
