#!/bin/bash

app_bak_home=/data/app_bak
app_home=$WORKSPACE_HOME/runtime/app
source $WORKSPACE_HOME/runtime/bin/util.sh
source $WORKSPACE_HOME/runtime/bin/common_env.sh
source /data/bin/common_env.sh

flag=1
if [ $1 ]; then
	for ((i = 0; i < ${#apps[@]}; i++)); do
		attr=(${apps[$i]})
		if [ ${attr[0]} = $1 ] || [ $1 = "all" ]; then
			flag=0
			echo "-----------------------------------------------------------------------------------------------"
			echo "开始备份${attr[0]}"
			bak_file_name=${attr[0]}_$(date +%Y_%m_%d_%H_%M_%S)
			if [ "${attr[1]}" != "3" ]; then
				cp -R $app_home/${attr[0]} $app_bak_home/${attr[0]}/$bak_file_name
				check "备份文件到$app_bak_home/${attr[0]}/$bak_file_name"
			else
				cp -R "$app_home/${attr[0]}".zip "$app_bak_home/${attr[0]}/$bak_file_name".zip
				check "备份文件到$app_bak_home/${attr[0]}/$bak_file_name".zip
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
