#!/bin/sh


#拷贝日志备份脚本
scp /data/app_logs/auto-del-90-days-ago-log.sh root@$1:/data/app_logs/
scp /data/app_logs/auto-zip-7-days-ago-log.sh root@$1:/data/app_logs/
scp /data/log_system.tar.gz root@$1:/data/
./rsync_nginx.sh $1
#同步与部署配置文件 用于隔离与部署环境和正式环境
rsync -avzr --progress --delete --exclude=sdk  --exclude=.svn: -e'ssh -p 22' /data/bin/conf/  root@$1:/data/bin/conf/
