#!/bin/bash

source $WORKSPACE_HOME/runtime/bin/env.sh
source $WORKSPACE_HOME/runtime/bin/util.sh
source /data/bin/common_env.sh

#log_name=`$WORKSPACE_HOME/runtime/bin/applogs.sh $1 $3`
#check=` echo "$log_name" | egrep "/data/app_logs"`
#if [ ! "$check" ];then
#  echo "$log_name"
#  exit 1;
#fi

for ((m = 0; m < ${#ips[@]}; m++)); do
	ip=(${ips[$m]})
	./rsync_nginx.sh $ip
	echo "$ip-------------------------------------------------"
done
