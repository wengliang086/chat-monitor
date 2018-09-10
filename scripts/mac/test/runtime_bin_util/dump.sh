#!/bin/bash

source env.sh
source util.sh

apps=("access_provider 1" "user_provider 1" "charge_open_api 2 2070" "access_open_api 2 2060" "access_web 2050")
###1代表provider 2代表web

flag=1
if [ $1 ]; then
	for ((i = 0; i < ${#apps[@]}; i++)); do
		attr=(${apps[$i]})
		if [ ${attr[0]} = $1 ] || [ $1 = "all" ]; then
			flag=0
			dump ${attr[0]}
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
