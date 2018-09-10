#!/bin/sh

basepath=$(
	cd $(dirname $0)
	pwd
)
cd $basepath

source runtime_bin_util/env.sh
source runtime_bin_util/util.sh

#把项目更新到指定目录
#参数（1、项目git地址，2、要更新到的路径，3、分支）

if [ $# -lt 2 ]; then
	echo "参数个数错误，必须输入 项目Git地址 和 目标路径"
fi

remote_url=$1
target_dir=$2
base_project_name=$3

echo ${remote_url}
echo ${target_dir}
echo ${base_project_name}

if [ ! -d $target_dir ]; then
	mkdir -p $target_dir
fi

cd $target_dir
if [ -d $base_project_name ]; then
	cd $base_project_name
	git pull
	check "Git Pull"
else
	git init
	git remote add origin $remote_url
	git clone $remote_url
	check "Git Clone"
	cd $base_project_name
fi
