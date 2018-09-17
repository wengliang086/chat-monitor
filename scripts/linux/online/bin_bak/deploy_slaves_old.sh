#!/bin/bash

source $WORKSPACE_HOME/runtime/bin/env.sh
source $WORKSPACE_HOME/runtime/bin/util.sh
source /data/bin/common_env.sh
source $WORKSPACE_HOME/runtime/bin/common_env.sh

flag=1

nginx_switch_restart() {
	group=$1
	# 感觉 本机（预部署）切换 好像没有必要
	if [ "$group" = "0" ]; then
	    # 恢复本机访问
		group=""
		sed -i "s/app_proxy_pass\/group.*\//app_proxy_pass\/group\//g" /usr/local/services/nginx/conf/nginx.conf
	else
	    # 把本机的访问转发到对应group的机器
		sed -i "s/app_proxy_pass\/group.*\//app_proxy_pass\/group$group\//g" /usr/local/services/nginx/conf/nginx.conf
	fi
	/usr/local/services/nginx/sbin/nginx -s reload
	# 部署特定的一台服务器时，有没有必要 切换其它机器的 Nginx ？？？？？
	# 1、如果不循环切换，同一时间一直会有7（n-1）台机器运行。（有新版本，有旧版本）
	# 2、如果执行循环切换，同一时间会有一个group的机器运行。（只有新版本 或者 只有旧版本）
	# 2正确，不过可以不用每台机器执行，每组部署开始执行一次就行
	for ((n = 0; n < ${#ips[@]}; n++)); do
		nginx_ip=(${ips[$n]})
		nginx_ip=(${nginx_ip[0]})
		echo "nginx远程重启$nginx_ip"
		./rsync_nginx.sh $nginx_ip
	done
}

#1、依次部署每一台机器
#2、部署特定一台机器时，依次部署每一个服务

flag=1
if [ $1 ]; then
	echo "ips $ips"
	for ((m = 0; m < ${#ips[@]}; m++)); do
		ip=(${ips[$m]})
		group=(${ip[1]})
		ip=(${ip[0]})

		group_switch="2"

		if [ "$group" = "2" ]; then
			group_switch="1"
		fi

		if [ ! "$2" = "skip_deploy" ]; then
			rsync -avzr --progress --delete --exclude '.svn' -e'ssh -p 22' /root/.bash_profile root@$ip:/root/.bash_profile
			rsync -avzr --progress --delete --exclude '.svn' -e'ssh -p 22' /data/workspace/ root@$ip:/data/workspace/
		fi
		# 切换标记，避免每部署一个WEB应用，都要执行所有机器的Nginx切换（每台机器，只切换一次 === 其实感觉 每组部署只需要切换一次？？？）
		nginx_is_switch=0
		for ((i = 0; i < ${#apps[@]}; i++)); do
			attr=(${apps[$i]})
			app=${attr[0]}
			type=${attr[1]}
			if [ ${attr[0]} = $1 ] || [ $1 = "all" ]; then
				flag=0
				if [ "$type" = "2" ]; then
				    # Web项目 分发通过 Nginx
					if [ "$nginx_is_switch" -eq 0 ]; then
						nginx_is_switch=1
						nginx_switch_restart $group_switch
						check "切换nginx到group"$group_switch
						sleep 2s
					fi
					./shell_remote.sh $ip "source /root/.bash_profile && /data/workspace/runtime/bin/restart.sh ${app}"
				else
				    # Provider 项目，分发通过 Zookeeper
					./shell_remote.sh $ip "source /root/.bash_profile && /data/workspace/runtime/bin/restart.sh ${app}"
				fi
			fi
		done

        # ？？？ 感觉应该放到所有机器部署完之后
        # 放到这里好像会造成，5秒的 新、旧 服务同时存在的情况
		if [ "$nginx_is_switch" -eq 1 ]; then
			sleep 5s
			nginx_switch_restart 0
			check "恢复nginx"
			sleep 5s
		fi

		echo "----------------------------------------------------------------------"
	done
	# ？？？ 感觉应该在这里还原
fi

if [ "$flag" -eq 1 ]; then
	echo "输入错误,请输入以下值，全部请输入all"
	for ((i = 0; i < ${#apps[@]}; i++)); do
		attr=(${apps[$i]})
		echo ${attr[0]}
	done
fi
