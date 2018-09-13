#!/bin/bash

#Jdk 版本
export JAVA_HOME=/data/workspace/common/jdk1.8.0_102

deploy_dir=/home/fastsdk/deploy/chat-moniter

# 只有最后一个参数有意义，为了跟内网脚本统一，这只是个入口脚本
./restart.sh t1 t2 t3 $deploy_dir
