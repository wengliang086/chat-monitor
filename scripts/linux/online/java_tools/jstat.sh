#!/bin/bash


source $WORKSPACE_HOME/runtime/bin/env.sh
source $WORKSPACE_HOME/runtime/bin/util.sh
source /root/bin/common_env.sh



ip="10.0.0.2"
if [ "$2" ];then
  ip=$2
fi

flag=0
if [ "$1" ];then 
app=""
for (( i=0; i<${#apps[@]}; i++  ))
do
    attr=(${apps[$i]})
    app=${attr[0]}
    if [ "$app" = "$1" ];then
      flag=1
      pid=`ssh -p36000 root@$ip "source /root/.bash_profile && $WORKSPACE_HOME/runtime/bin/pid.sh $1 "`
      /root/bin/shell_remote.sh $ip "/root/workspace/common/jdk1.7.0_60/bin/jstat -gccause $pid  1s "
    fi 
done
fi

if [ $flag -eq 0 ];then
  echo "输入错误,请输入以下值，全部请输入all"
  for (( i=0; i<${#apps[@]}; i++  ))
  do
    attr=(${apps[$i]})
    echo ${attr[0]}
  done
fi

