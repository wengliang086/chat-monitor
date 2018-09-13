#!/bin/bash
source /data/bin/common_env.sh

rsync -avzr --progress --delete --exclude '.svn' -e'ssh -p 22' /root/.bash_profile  root@$1:/root/.bash_profile
rsync -avzr --progress --delete --exclude '.svn': -e'ssh -p 22' /data/workspace/  root@$1:/data/workspace/ 
#rsync -avzr --progress --delete --exclude '.svn' --exclude  'android-sdk-linux' -e'ssh -p 36000' /root/workspace/  root@$1:/root/workspace/
