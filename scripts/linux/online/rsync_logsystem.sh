#!/bin/bash
source /data/bin/common_env.sh

rsync -avzr --progress --delete --exclude '.svn': -e'ssh -p 22' /data/log_system/logstash-1.4.2/bin/*.sh  root@$1:/data/log_system/logstash-1.4.2/bin/
rsync -avzr --progress --delete --exclude '.svn': -e'ssh -p 22' /data/log_system/logstash-1.4.2/bin/*.conf  root@$1:/data/log_system/logstash-1.4.2/bin/
ssh root@$1  "source /root/.bash_profile && /data/log_system/logstash-1.4.2/bin/restart_logstash.sh"
