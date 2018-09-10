#!/bin/sh
basepath=$(
	cd $(dirname $0)
	pwd
)
cd $basepath

source runtime_bin_util/util.sh

echo "1、更新"
./version_update_git.sh $*
check "1、更新"

echo "2、构建"
./build.sh $*
check "2、构建"

echo "3、上传"
./upload.sh $*
check "3、上传"

echo "4、部署"
./deploy.sh $*
check "4、部署"

echo "5、重启"
cd $basepath
runtime_bin_util/restart.sh "all"
