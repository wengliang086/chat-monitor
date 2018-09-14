#!/bin/bash

local_deploy_home=/home/fastsdk/deploy
source $WORKSPACE_HOME/runtime/bin/env.sh
source $WORKSPACE_HOME/runtime/bin/util.sh
source /data/bin/common_env.sh
source $WORKSPACE_HOME/runtime/bin/common_env.sh

###1代表provider 2代表web

flag=1
if [ $1 ]; then
	for ((i = 0; i < ${#apps[@]}; i++)); do
		attr=(${apps[$i]})
		if [ "${attr[0]}" = $1 ] || [ $1 = "all" ]; then
			flag=0
			echo "-----------------------------------------------------------------------------------------------"
			echo "开始部署${attr[0]}"
			#rm -rf $WORKSPACE_HOME/runtime/app/${attr[0]}
			if [ ${attr[1]} = "1" ]; then
				if [ ! -f "$local_deploy_home/${attr[0]}-assembly.tar.gz" ]; then
					echo "没有部署的文件$local_deploy_home/${attr[0]}-assembly.tar.gz"
					exit 1
				fi
				rm -rf $WORKSPACE_HOME/runtime/app/${attr[0]}
				tar -zxvf $local_deploy_home/${attr[0]}-assembly.tar.gz -C $WORKSPACE_HOME/runtime/app/
				check "部署文件到$WORKSPACE_HOME/runtime/app/${attr[0]}"
				rm -rf $local_deploy_home/${attr[0]}-assembly.tar.gz
				$WORKSPACE_HOME/runtime/bin/restart.sh ${attr[0]} $2
			elif [ ${attr[1]} = "2" ]; then
				if [ ! -f "$local_deploy_home/${attr[0]}.war" ]; then
					echo "没有部署的文件$local_deploy_home/${attr[0]}.war"
					exit 1
				fi
				rm -rf $WORKSPACE_HOME/runtime/app/${attr[0]}
				unzip $local_deploy_home/${attr[0]}.war -d $WORKSPACE_HOME/runtime/app/${attr[0]}
				check "部署文件到$WORKSPACE_HOME/runtime/app/${attr[0]}"
				rm -rf $local_deploy_home/${attr[0]}.war
				$WORKSPACE_HOME/runtime/bin/restart.sh ${attr[0]} $2
			else
				if [ ! -f "$local_deploy_home/${attr[0]}.zip" ]; then
					echo "没有部署的文件$local_deploy_home/${attr[0]}.zip"
					exit 1
				fi
				rm -rf $WORKSPACE_HOME/runtime/app/${attr[0]}.zip
				rm -rf $WORKSPACE_HOME/runtime/app/${attr[0]}
				cp -R $local_deploy_home/${attr[0]}.zip $WORKSPACE_HOME/runtime/app/
				unzip $WORKSPACE_HOME/runtime/app/${attr[0]}.zip -d $WORKSPACE_HOME/runtime/app/
				./packing.sh $WORKSPACE_HOME/runtime/app ${attr[0]}
				rm -rf $local_deploy_home/${attr[0]}.zip
			fi

		fi
	done
fi

if [ $flag -eq 1 ]; then
	echo "输入错误,请输入以下值，全部请输入all"
	for ((i = 0; i < ${#apps[@]}; i++)); do
		attr=(${apps[$i]})
		echo ${attr[0]}
	done
fi
