#!/bin/sh

basePath=$(
	cd $(dirname $0)
	pwd
)
echo $basePath

git_url=http://code.hoolai.com/git/liyongxiang/chat-monitor.git
#这里可以使用 指定目录 或者 当前目录
#basePath=/Users/access/scripts/
base_project_name=chat-monitor

check() {
	if [ $? -ne 0 ]; then
		echo "===================$1,执行失败======================="
		exit 1
	fi
	echo "===================$1,执行成功========================"
}

#指定basePath时，目录可能需要创建
if [ ! -d $basePath ]; then
	mkdir -p $basePath
	cd $basePath
fi

if [ -d $base_project_name ]; then
	cd $base_project_name
	# 因为chmod的执行，导致scripts目录下的文件已经被更改
	git reset --hard
	git pull
	check "Git Pull"
else
	git init
	git remote add origin $git_url
	git clone $git_url
	check "Git Clone"
	cd $base_project_name
fi

chmod -R +x scripts

echo "输入参数列表："$@

if [ $# -eq 0 ]; then
	echo "必须输入脚本名称"
	exit 1
fi

process_dir=${basePath}/targetDir
deploy_dir=${basePath}/deployDir
./scripts/mac/test/$1 $git_url $process_dir $base_project_name $deploy_dir
#./$1 $2 $3 $4 $5 $6 $7 $8
