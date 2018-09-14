#!/bin/bash

source $WORKSPACE_HOME/runtime/bin/env.sh
source $WORKSPACE_HOME/runtime/bin/util.sh
source /data/bin/common_env.sh

app_bak=/data/app_bak/$1
app_home=$WORKSPACE_HOME/runtime/app
flag=1
if [ $1 ]; then
	for ((i = 0; i < ${#apps[@]}; i++)); do
		attr=(${apps[$i]})
		if [ ${attr[0]} = $1 ] || [ $1 = "all" ]; then
			flag=0
			echo "$2" | grep -q "${attr[0]}"
			if [ $? -ne 0 ]; then
				echo "$1不能使用文件$2回滚"
				exit 1
			fi
			echo "-----------------------------------------------------------------------------------------------"
			if [ "${attr[1]}" != "3" ]; then
				echo "开始回滚${attr[0]} 文件 $app_bak/$2"
				rm -rf $app_home/$1
				check "清除$app_home/$1"
				cp -R $app_bak/$2 $app_home/$1
				check "回滚$app_bak/$2"
				$WORKSPACE_HOME/runtime/bin/restart.sh $1 skip
			else
				echo "开始回滚${attr[0]} 文件 $app_bak/$2"
				rm -rf "$app_home/$1".zip
				rm -rf $app_home/$1
				check "清除$app_home/$1"
				cp -R $app_bak/$2 "$app_home/$1".zip
				unzip "$app_home/$1".zip -d $app_home/
				check "回滚$app_bak/$2"
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
