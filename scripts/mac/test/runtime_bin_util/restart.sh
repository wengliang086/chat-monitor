#!/bin/bash

basepath=$(
	cd $(dirname $0)
	pwd
)
cd $basepath

source env.sh
source util.sh

function setJavaMemOps() {
	XMS_SIZE=256M
	XMX_SIZE=256M
	XMN_SIZE=128M
	PERM_SIZE=128M
	MAX_PERM_SIZE=128M
	setAppJavaMemOps $1 log_provider 512 512 256 128 128
	setAppJavaMemOps $1 access_open_api 396 396 128 128 128
}

function setAppJavaMemOps() {
	if [ $1 == $2 ]; then
		XMS_SIZE=$3M
		XMX_SIZE=$4M
		XMN_SIZE=$5M
		PERM_SIZE=$6M
		MAX_PERM_SIZE=$7M
	fi
}

echo ${#apps[@]}
if [ "$1" != "packing_client_swt" ]; then
	flag=1
	if [ $1 ]; then
		for ((i = 0; i < ${#apps[@]}; i++)); do
			attr=(${apps[$i]})
			if [ ${attr[0]} = $1 ] || [ $1 = "all" ]; then
				#if [ ${attr[0]} = 'log_provider' ];then
				setJavaMemOps ${attr[0]}
				flag=0
				echo "-------------------------------------------------------------"
				if [ ${attr[1]} = "1" ]; then
					$WORKSPACE_HOME/runtime/app/${attr[0]}/bin/restart.sh skip
				elif [ ${attr[1]} = "3" ]; then
					echo "${attr[0]}不需要重启"
				else
					if [ "${attr[0]}" != "access_web" ]; then
						restart_web ${attr[2]} ${attr[0]} $WORKSPACE_HOME/runtime/app/${attr[0]} skip
					else
						skip=$2
						if [ "$skip" = "" ]; then
							skip="not_skip"
						fi
						restart_web ${attr[2]} ${attr[0]} $WORKSPACE_HOME/runtime/app/${attr[0]} skip "use_config"
					fi
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
fi
