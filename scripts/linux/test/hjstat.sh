#!/bin/sh

source $WORKSPACE_HOME/runtime/bin/env.sh
source $WORKSPACE_HOME/runtime/bin/util.sh


for (( i=0; i<${#apps[@]}; i++  ))
do
    attr=(${apps[$i]})
    if [ ${attr[0]} = $1 ];then
      pid=`ps -ef | grep $1 | grep -v grep|grep -v $0 | awk   '{ print $2}'` 
      jstat -gccause $pid 5s
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




