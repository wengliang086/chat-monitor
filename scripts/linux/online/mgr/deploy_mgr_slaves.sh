#!/bin/bash

ipConfigs=("$server_log 1 15")
source ./env.sh

deploy_dir=/home/fastsdk/deploy

#1、依次部署每一台机器
#2、部署特定一台机器时，依次部署每一个服务

flag=1
if [ $1 ]; then
	echo "ipConfigs $ipConfigs"
	for ((m = 0; m < ${#ipConfigs[@]}; m++)); do
		ipConfig=(${ipConfigs[$m]})
		ip=(${ipConfig[0]})

		if [ ! "$2" = "skip_deploy" ]; then
			rsync -avzr --progress --delete --exclude '.svn' -e'ssh -p 22' /data/bin/mgr/ root@$ip:/data/bin/mgr/
			rsync -avzr --progress --delete --exclude '.svn' -e'ssh -p 22' ${deploy_dir}/ root@$ip:${deploy_dir}/
		fi

		for ((i = 0; i < ${#apps[@]}; i++)); do
			attr=(${apps[$i]})
			app=${attr[0]}
			if [ ${app} = $1 ] || [ $1 = "all" ]; then
				flag=0
				./shell_remote.sh $ip "cd /data/bin/mgr/ && /data/bin/mgr/deploy_mgr_master.sh ${app}"
			fi
		done

		echo "----------------------------------------------------------------------"
	done
fi

if [ "$flag" -eq 1 ]; then
	echo "输入错误,请输入以下值，全部请输入all"
	for ((i = 0; i < ${#apps[@]}; i++)); do
		attr=(${apps[$i]})
		echo ${attr[0]}
	done
fi
