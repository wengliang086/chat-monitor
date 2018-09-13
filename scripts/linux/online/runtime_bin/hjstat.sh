#!/bin/sh

source $WORKSPACE_HOME/runtime/bin/common_env.sh
#source $WORKSPACE_HOME/runtime/bin/util.sh


for (( i=0; i<${#apps[@]}; i++  ))
do
    attr=(${apps[$i]})
    if [ ${attr[0]} = "$1" ] ||  [ "$1" == "all" ];then
      if [ ${attr[0]} != "packing_client_swt" ];then
      
      #ps -ef | grep ${attr[0]} 
      pid=`ps -ef --width 2000 | grep ${attr[0]} |grep -v watch| grep -v grep|grep -v hjstat |grep -v vim |grep -v tail|grep -v rotatelogs | awk   '{ print $2}'` 
      echo "------------------------------------------pid:$pid,${attr[0]}----------------------------------"
      jstat -gccause $pid $2 
      fi
      #ps -ef | grep $1 | grep -v "grep" |grep  $0
      #pid=`ps -ef|grep $1|grep -v "grep"| awk   '{ print $2}'` 
      #echo "$pid" 
      #echo "pid $pid"
    fi
done



if [ ! $1 ];then
  echo "输入错误,请输入以下值"
  for (( i=0; i<${#apps[@]}; i++  ))
  do
    attr=(${apps[$i]})
    echo ${attr[0]}
  done
fi




