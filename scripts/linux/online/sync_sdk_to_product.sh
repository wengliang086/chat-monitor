#!/bin/sh

sdk_deploy_dir=/data/sharedata/access/version_sdk
dev_sdk_deploy_dir=/data/sharedata/access/dev_sdk
sdk_dir=/data/sharedata/access/sdk

check(){
    if [ $? -ne 0 ];then
     echo "===================$1,执行失败======================="
     exit 1;
    fi
     echo "===================$1,执行成功========================"
}

checkExist(){
	if [ -d "$dev_sdk_deploy_dir/$1" ];then
		pushd "$dev_sdk_deploy_dir/$1"
		if ls *.apk >/dev/null 2>&1;then
			echo "可以部署sdk"
		else
			echo "没有可部署的sdk"
			exit 1;
		fi
		popd
	else
		echo "没有可部署的sdk"
		exit 1;
	fi
}

if [ "all" = "$1" ] ; then
    deploy_product_dir=$sdk_deploy_dir"/"
    if [ ! -d "$deploy_product_dir" ];then
        mkdir -p $deploy_product_dir
    fi

    rsync -avr $dev_sdk_deploy_dir"/"* $deploy_product_dir
    check $1"同步到生产环境"

    #rm -rf $dev_sdk_deploy_dir"/"*
    ./cp_source_new_target.sh $1 $sdk_deploy_dir $sdk_dir
else
    checkExist $1

    deploy_product_dir=$sdk_deploy_dir"/"$1
    if [ ! -d "$deploy_product_dir" ];then
	mkdir -p $deploy_product_dir
    fi

    rsync -avr $dev_sdk_deploy_dir"/"$1"/"* $deploy_product_dir
    check $version_sdk_channel"_"$sdk_version".apk同步到生产环境"

    rm -rf $dev_sdk_deploy_dir"/"$1"/"* 
    ./cp_source_new_target.sh $1 $deploy_product_dir $sdk_dir
fi

#basepath=$(cd `dirname $0`; pwd)
#sh $basepath/sync_sdk_version_database.sh $channel

curl localhost:2015/access_admin/sdk_version/urlSyncSDKData.hl?channel=$1

echo "部署完成"

