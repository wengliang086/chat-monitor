#!/bin/bash

source env.sh
source util.sh
source common_env.sh

flag=0
if [ "$1" ]; then
	app=""
	for ((i = 0; i < ${#apps[@]}; i++)); do
		attr=(${apps[$i]})
		app=${attr[0]}
		if [ "$app" = "$1" ]; then
			flag=1
			ps -ef | grep $1 | grep -v "grep" | grep -v "$0" | grep -v "rotatelogs" | grep -v "jstack" | grep -v "jstat" | awk '{print $2}'
		fi
	done
fi

if [ $flag -eq 0 ]; then
	echo "输入错误,请输入以下值，全部请输入all"
	for ((i = 0; i < ${#apps[@]}; i++)); do
		attr=(${apps[$i]})
		echo ${attr[0]}
	done
fi
