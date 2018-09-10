#!/bin/sh

demo_build_dir=/root/fastaccess/build/fast_access_sdk_dir_online
sdk_deploy_dir=/root/access/fastaccess_web/download/demo_online

apps=("hoolai_sdk_demo" "hoolai_sdk_demo_JustPay" "hoolai_MSDK_Demo")

if [ $# -ne 2 ];then
	echo "输入错误，请输入以下值加部署版本号，或者全部请输入all加部署版本号"
	echo ${apps[*]}
	exit 1;
fi

version_code=$2

checkParas(){
	tag="false"
	for((k=0;k<${#apps[@]};k++))
		do
			if [ ${apps[k]} = $1 ]; then
				tag="true"
				break
			fi
		done
	if [ "$tag" = "false" ] ; then
		echo "输入参数$1不合法"
		exit 1;
	fi
}

check(){
  if [ $? -ne 0 ];then
	echo "===================$1,执行失败======================="
	exit 1;
  fi
	echo "===================$1,执行成功========================"
}

deploy(){
	rm -rf $demo_build_dir/$1
	svn co http://svn.hoolai.com/access-platform/branches/fast_access_sdk/$version_code/fast_access_sdk/$1 -r head $demo_build_dir/$1
	check "$1更新成功"

	rsync -avr --exclude '.svn'  $demo_build_dir/$1 $sdk_deploy_dir
	check "$1删除.svn文件夹"

	cd $sdk_deploy_dir
	mv $1/readme.txt ./

	rm -rf $1.zip
	zip -r $1.zip readme.txt $1
	check "$1部署成功"
	rm -rf $1
}

upload(){
	scp -P36000 $sdk_deploy_dir/"$1".zip fastsdk@60.28.203.117:/home/fastsdk/deploy/
	check "上传$1到包正式环境"
}

deployMSDK(){
	deploy "hoolai_MSDK_LibProject"
	deploy "hoolai_sdk_pay_LibProject"
	deploy "hoolai_sdk_demo_MSDK"
	cd $sdk_deploy_dir
	zip $1.zip "hoolai_MSDK_LibProject.zip" "hoolai_sdk_pay_LibProject.zip" "hoolai_sdk_demo_MSDK.zip"
	check "$1部署成功"
	rm -rf "hoolai_MSDK_LibProject.zip" "hoolai_sdk_pay_LibProject.zip" "hoolai_sdk_demo_MSDK.zip"
}

if [ $1 = "all" ];then
	for (( i=0; i<${#apps[@]}; i++ ))
		do
			if [ "hoolai_MSDK_Demo" = ${apps[$i]} ];then
				deployMSDK ${apps[$i]}
			else
				deploy ${apps[$i]}
			fi
			upload ${apps[$i]}
		done
else
	checkParas $1
	if [ "hoolai_MSDK_Demo" = $1 ];then
		deployMSDK $1
	else
		deploy $1
	fi
	upload $1
fi



