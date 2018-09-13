#!/bin/sh

rsync -avzr --progress --delete --exclude '.svn' -e'ssh -p 22' /root/.bash_profile  root@$1:/root/.bash_profile
rsync -avzr --progress --delete --exclude '.svn': -e'ssh -p 22' /data/workspace/  root@$1:/data/workspace/
/data/bin/shell_remote.sh $1  "source /root/.bash_profile && /data/workspace/runtime/bin/restart.sh $2"
