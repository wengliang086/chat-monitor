#!/bin/bash

source /root/workspace/runtime/bin/env.sh

remote_deploy_home=/root/deploy/test
local_deploy_home=$WORKSPACE_HOME/runtime/app




flag=1
if [ $1 ];then
for (( i=0; i<${#apps[@]}; i++  ))
do
    attr=(${apps[$i]})
    if  [ ${attr[0]} = $1 ] || [ $1 = "all" ];then
        flag=0
        echo "-----------------------------------------------------------------------------------------------"
        echo "开始部署${attr[0]}"
	rm -rf $local_deploy_home/${attr[0]}
        if [ "${attr[1]}" = "1" ];then 
           tar -zxvf  $remote_deploy_home/${attr[0]}-assembly.tar.gz -C $local_deploy_home/
           check "部署文件到$WORKSPACE_HOME/runtime/app/${attr[0]}"
        elif [ "${attr[1]}" = "2"  ];then 
           unzip $remote_deploy_home/${attr[0]}.war -d $local_deploy_home/${attr[0]}
           check "部署文件到$WORKSPACE_HOME/runtime/app/${attr[0]}"
        else
           cp -R $remote_deploy_home/${attr[0]}.zip  $local_deploy_home/
           unzip $local_deploy_home/${attr[0]}.zip -d $local_deploy_home/
	   ./packing.sh $local_deploy_home ${attr[0]}
           check "部署文件到$WORKSPACE_HOME/runtime/app/${attr[0]}.zip"
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
