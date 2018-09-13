#!/bin/bash
source $WORKSPACE_HOME/runtime/bin/util.sh
source /data/bin/common_env.sh


for (( m=0; m<${#ips[@]}; m++  ))
do
ip=(${ips[$m]})
group=(${ip[1]})
ip=(${ip[0]})

./rsync_nginx.sh $ip
done



