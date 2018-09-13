#!/bin/bash

source $WORKSPACE_HOME/runtime/bin/env.sh
source $WORKSPACE_HOME/runtime/bin/util.sh
source $WORKSPACE_HOME/runtime/bin/common_env.sh



if [ "$1" != "packing_client_swt" ];then
flag=1
if [ $1 ];then
for (( i=0; i<${#apps[@]}; i++  ))
do
    attr=(${apps[$i]})
    if [ ${attr[0]} = $1 ] || [ $1 = "all" ];then
        flag=0
        echo "-------------------------------------------------------------"
	if [ ${attr[1]} = "1" ];then         
           $WORKSPACE_HOME/runtime/app/${attr[0]}/bin/stop.sh $2 stop
        else   
           stop_web ${attr[2]} ${attr[0]} $WORKSPACE_HOME/runtime/app/${attr[0]} $2 stop
 	fi
    fi
done
fi

if [ $flag -eq 1 ];then
  echo "输入错误,请输入以下值，全部请输入all"
  for (( i=0; i<${#apps[@]}; i++  ))
  do
    attr=(${apps[$i]})
    echo ${attr[0]}
  done
fi
fi
