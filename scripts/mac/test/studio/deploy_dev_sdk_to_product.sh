#!/bin/sh

sdk_deploy_dir=$WORKSPACE_HOME/deploy/sdk/version_sdk
dev_sdk_deploy_dir=$WORKSPACE_HOME/deploy/sdk/dev_sdk

check() {
	if [ $? -ne 0 ]; then
		echo "===================$1,执行失败======================="
		exit 1
	fi
	echo "===================$1,执行成功========================"
}

checkExist() {
	if [ -d "$dev_sdk_deploy_dir/$1" ]; then
		pushd "$dev_sdk_deploy_dir/$1"
		if ls *.apk >/dev/null 2>&1; then
			echo "可以部署sdk"
		else
			echo "没有可部署的sdk"
			exit 1
		fi
		popd
	else
		echo "没有可部署的sdk"
		exit 1
	fi
}

checkExist $1

deploy_product_dir=$sdk_deploy_dir"/"$1
if [ ! -d "$deploy_product_dir" ]; then
	mkdir -p $deploy_product_dir
fi

rsync -avr $dev_sdk_deploy_dir"/"$1"/"* $deploy_product_dir
check $version_sdk_channel"_"$sdk_version".apk部署到内网生产"

rm -rf $dev_sdk_deploy_dir"/"$1"/"*

echo "部署完成"
