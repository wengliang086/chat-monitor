#!/bin/sh

basepath=$(
	cd $(dirname $0)
	pwd
)
cd $basepath

source runtime_bin_util/env.sh
source runtime_bin_util/util.sh

deploy_dir=$4

for (( i = 0; i < ${#apps[@]}; i++ )); do
	app=(${apps[$i]})
	echo "app="$app
done

scp $deploy_dir/*.jar fastsdk@119.29.21.153:/home/fastsdk/deploy/chat-moniter/
check "上传所有包到正式环境online_tx"
#echo "upload 忽略"
