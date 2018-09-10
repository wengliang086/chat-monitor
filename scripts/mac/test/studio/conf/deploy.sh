#!/bin/bash

check() {
	if [ $? -ne 0 ]; then
		echo "===================$1,执行失败======================="
		exit 1
	fi
	echo "===================$1,执行成功========================"
}

deploy() {
	basepath=$(
		cd $(dirname $0)
		pwd
	)
	project_name_prefix=$1
	target_apk_name=$1
	if [ $3 ]; then
		target_apk_name=$3
	fi
	target_apk_path=$sdk_deploy_dir/"$target_apk_name"_fastaccess_sdk.apk
	base_project_dir=$sdk_build_dir/"$project_name_prefix"_fastaccess_sdk

	cd $base_project_dir
	rm -rf local.properties
	#sed -i "s/22.0.0/22.0.1/g" ./app/build.gradle
	echo "=================== $1 打包 ==================="
	if [ ! -f "sdk_version.txt" ]; then
		echo "=================== 版本文件不存在 ==================="
		exit 1
	fi
	if [ ! -d "app/src/main/assets" ]; then
		mkdir "app/src/main/assets"
		echo "=================== 创建assets目录 ==================="
	fi
	dateStr=$(date)
	echo $dateStr
	#在mac上，下面的sed指令不好使
	#sed "1i\部署日期：$dateStr" sdk_version.txt > "app/src/main/assets/sdk_version.txt"
	cp sdk_version.txt app/src/main/assets/sdk_version.txt
	echo "部署日期：$dateStr" >>app/src/main/assets/sdk_version.txt
	#gradle build
	sh gradlew build
	check "$1打包"
	rm -rf $target_apk_path
	if [ -d "./app/build/outputs/apk/release/" ]; then
		rsync -avr ./app/build/outputs/apk/release/app-release-unsigned.apk $target_apk_path
	else
		rsync -avr ./app/build/outputs/apk/app-release-unsigned.apk $target_apk_path
	fi
	check "$1部署"

	cd $basepath
	echo "模式$2"
	echo $(pwd)

	sh ../../util_sdk/muti_version_sdk.sh $base_project_dir/sdk_version.txt $target_apk_path $target_apk_name $2
}

deploy $@
