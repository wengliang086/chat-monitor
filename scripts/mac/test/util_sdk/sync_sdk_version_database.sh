#!/bin/bash

source ../runtime_bin_util/env.sh
source ../runtime_bin_util/util.sh

channel=$1

echo "准备同步后台数据库sdk版本信息！！！"

for ((appIndex=0; appIndex<${#apps[@]}; appIndex++ ));
  do
	appConfigAttr=(${apps[$appIndex]})
	echo "检测渠道：${appConfigAttr[0]}"
	if [ "access_admin" = "${appConfigAttr[0]}" ];then
		echo "端口：${appConfigAttr[2]}"
		curl localhost:${appConfigAttr[2]}/access_admin/sdk_version/urlSyncSDKData.hl?channel=$channel
	fi
done



