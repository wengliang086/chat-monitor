#!/bin/bash

basepath=$(
	cd $(dirname $0)
	pwd
)
cd $basepath

source env.sh
source util.sh

baseDir=$WORKSPACE_HOME

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

echo "总项目数="${#apps[@]}
flag=1
if [ $1 ]; then
    for ((i = 0; i < ${#apps[@]}; i++)); do
        attr=(${apps[$i]})
        p_name=${attr[0]}
        p_type=${attr[1]}
        p_port=${attr[2]}
        if [ $p_name = $1 ] || [ $1 = "all" ]; then
            setJavaMemOps $p_name
            flag=0
            echo "-------------------------------------------------------------"
            if [ ${p_type} = "1" ]; then
                #非web项目
                $baseDir/runtime/app/${p_name}/bin/restart.sh skip
            elif [ ${p_type} = "3" ]; then
                #非启动项目
                echo "${attr[0]}不需要重启"
            elif [ ${p_type} = "4" ]; then
                #SpringBoot Jar 项目
                springboot_restart.sh ${p_port} ${p_name}
            else
                #平常web项目
                if [ ${p_name} != "access_web" ]; then
                    restart_web ${p_port} ${p_name} $baseDir/runtime/app/${p_name} skip
                else
                    skip=$2
                    if [ "$skip" = "" ]; then
                        skip="not_skip"
                    fi
                    restart_web ${p_port} ${p_name} $baseDir/runtime/app/${p_name} skip "use_config"
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
