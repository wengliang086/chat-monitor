#!/bin/bash

basepath=$(cd `dirname $0`; pwd)
cd $basepath

source env.sh
source util.sh


apps=("access_provider 1" "user_provider 1" "query_privider" "charge_open_api 2 2070"  "access_open_api 2 2060" "access_web 2050")
###1代表provider 2代表web


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
           $WORKSPACE_HOME/runtime/app/${attr[0]}/bin/stop.sh $2
        else   
           stop_web ${attr[2]} ${attr[0]} $WORKSPACE_HOME/runtime/app/${attr[0]} $2
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
