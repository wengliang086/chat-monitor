#!/bin/bash

#Jdk 版本
export JAVA_HOME=/data/workspace/common/jdk1.8.0_102

deploy_dir=/home/fastsdk/deploy/chat-moniter

# 为了复用内网脚本（参数：1、appName，2、无意义，3、无意义，4、部署目录）
./restart.sh $1 t2 t3 $deploy_dir
