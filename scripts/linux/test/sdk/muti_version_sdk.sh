 #!/bin/bash

sdk_deploy_dir=/root/access/fastaccess_web/version_sdk
remote_sdk_deploy_dir=

#包含version_sdk.txt目录
file_name=$1

#将要部署apk路径
apk_file=$2

#部署渠道
channel=$3

check(){
  if [ $? -ne 0 ];then
   echo "===================$1,执行失败======================="
   exit 1;
  fi
   echo "===================$1,执行成功========================"
}

deploy_version_sdk(){
	version_sdk_channel=$1
	version_sdk_dir=$2
	version_sdk_file=$3
	sdk_version=$4
	deploy_version_sdk_dir=$version_sdk_dir"/"$version_sdk_channel
	if [ ! -d $deploy_version_sdk_dir ];then
			mkdir -p $deploy_version_sdk_dir
	fi
	
	deploy_sdk_version_file=$deploy_version_sdk_dir"/"$version_sdk_channel"_"$sdk_version".apk"
	
	rsync -avr $version_sdk_file $deploy_sdk_version_file
	check "$version_sdk_channel"_"$sdk_version".apk"部署到内网
	
	#rsync -avr $deploy_sdk_version_file $remote_sdk_deploy_dir
	#check "$version_sdk_channel"_"$sdk_version".apk"部署到外网
}

first_line=$(sed -n '1p' $file_name)

echo "result="$first_line

version=`echo $first_line | awk -F '：' '{print $2}'`

if [ -z "$version" ] || [ "$version" = "" ];then
	version=`echo $first_line | awk -F ':' '{print $2}'`
	if [ -z "$version" ] || [ "$version" = "" ];then
		echo "版本信息配置有误，请重新配置打包"
		exit 1;
	fi
fi

echo "version="$version


#发送apk到内网目录
#发送apk到外网目录

deploy_version_sdk "$channel" "$sdk_deploy_dir" "$apk_file" "$version"
