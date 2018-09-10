#!/bin/sh

demo_build_dir=$WORKSPACE_HOME/build/fast_access_sdk_dir
sdk_deploy_dir=$WORKSPACE_HOME/deploy/demo
demo_name=hoolai_sdk_air_demo

if [ $# -ne 1 ]; then
	echo "输入错误,请输入test或online"
	exit 1
fi

check() {
	if [ $? -ne 0 ]; then
		echo "===================$1,执行失败======================="
		exit 1
	fi
	echo "===================$1,执行成功========================"
}

deploy() {
	rm -rf $demo_build_dir/$1
	svn co http://svn.hoolai.com/access-platform/trunk/fast_access_sdk/HLSDKExtensionDemo -r head $demo_build_dir/$1
	check "$1更新成功"

	rsync -avr --exclude '.svn' $demo_build_dir/$1 $sdk_deploy_dir
	check "$1删除.svn文件夹"

	cd $sdk_deploy_dir
	rm -rf $1.zip
	cd $1
	zip -r $1.zip *
	check "$1部署成功"
	mv $1.zip ..
	cd ..
	rm -rf $1
}

upload() {
	scp -P22 $sdk_deploy_dir/"$1".zip fastsdk@119.29.21.153:/home/fastsdk/deploy/
	check "上传$1到包正式环境"
}

addDocs() {
	svn co http://svn.hoolai.com/access-platform/trunk/doc/Android -r head $demo_build_dir/Android
	check "文档更新成功"
	rsync -avr --exclude '.svn' $demo_build_dir/Android/ $sdk_deploy_dir/$1
	check "文档删除.svn文件夹"
	mv $1.zip $1/$1_src.zip
	cd $1
	zip -r $1.zip *
	check "文档部署成功"
	mv $1.zip ..
	cd ..
	rm -rf $1
}

deploy $demo_name
#addDocs $demo_name
if [ "online" = $1 ]; then
	upload $demo_name
fi
