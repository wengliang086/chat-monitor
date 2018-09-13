#!/bin/bash

trap 'kill $(jobs -p)' EXIT

source $WORKSPACE_HOME/runtime/bin/env.sh
source $WORKSPACE_HOME/runtime/bin/util.sh

ips=("$server_s5 1" "$server_s6 1" "$server_s7 2" "$server_s8 2")

if [ ! "$1" ];then
 echo "没有输入应用名,例子 ./rtail.sh app regex date"
 exit 0;
fi

log_name=`$WORKSPACE_HOME/runtime/bin/applogs.sh $1 $3`
check=` echo "$log_name" | egrep "/data/app_logs"`
if [ ! "$check" ];then
  echo "$log_name"
  exit 1;
fi



commond="tail -f $log_name"
echo $commond
for (( m=0; m<${#ips[@]}; m++  ))
do
ip=(${ips[$m]})
if [ "$2" ];then
ssh -p22 $ip "$commond" | grep "$2" &
else
ssh -p22 $ip "$commond"  &
fi
done

# wait .. until CTRL+C
wait
