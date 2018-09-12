#!/bin/sh

#Jdk 版本
JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_91.jdk/Contents/Home
echo $JAVA_HOME

basePath=$(
	cd $(dirname $0)
	pwd
)
#这里可以使用 指定目录 或者 当前目录
#basePath=/Users/access/scripts/
#echo $basePath
process_dir=${basePath}/targetDir
deploy_dir=${basePath}/deployDir

#指定basePath时，目录可能需要创建
if [ ! -d $basePath ]; then
	mkdir -p $basePath
	cd $basePath
fi


echo "输入参数列表："$@

if [ $# -eq 0 ]; then
    echo "\033[0;31m 未输入操作名 \033[0m  \033[0;34m {all|server|front|update} \033[0m"
	exit 1
fi

# front t1
t1=$2

git_url=http://code.hoolai.com/git/liyongxiang/chat-monitor.git
base_project_name=`echo ${git_url##*/} | sed 's/.git//'`

check() {
	if [ $? -ne 0 ]; then
		echo "===================$1,执行失败======================="
		exit 1
	fi
	echo "===================$1,执行成功========================"
}

function update() {
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
}

#服务器端部署 参数（1、脚本名称）
server() {
    update
    ./scripts/mac/test/$1 $git_url $process_dir $base_project_name $deploy_dir
}

#部署前端
front() {
    update
    cp -r ./admin/admin ${deploy_dir}
    cd $deploy_dir/admin

    if [ $t1 = "test" ]; then
        sed -i "_bak" "s/10.1.1.236:20090/10.1.1.253:20090/" ./config/prod.env.js
    fi

    echo `pwd`
    if [ ! -d "node_modules" ]; then
        echo "npm install start ..."
        npm install
    fi
    echo "npm build start ..."
    npm run build

    if [ ! -d $deploy_dir/chat-admin ]; then
        mkdir $deploy_dir/chat-admin
    fi
    cp -r ./dist/ $deploy_dir/chat-admin/

    if [ -f $deploy_dir/chat-admin.zip ]; then
        rm -f $deploy_dir/chat-admin.zip
    fi

    cd $deploy_dir
    zip -r chat-admin.zip ./chat-admin
    scp chat-admin.zip fastsdk@119.29.21.153:/home/fastsdk/deploy/chat-moniter/
    check "上传 chat-admin.zip 到正式环境 online_tx"
}

all() {
    update
    server $1
    front
}

script_name="build_deploy_restart.sh"

case $1 in
        all) all ${script_name};;
        update) update;;
        server) server ${script_name};;
        front) front;;
        *) echo -e "\033[0;31m Usage: \033[0m  \033[0;34m sh  $0  {all|server|front|update} \033[0m
    \033[0;31m Example: \033[0m  \033[0;33m sh  $0  server \033[0m"
esac
