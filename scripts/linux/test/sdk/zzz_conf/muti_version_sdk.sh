 #!/bin/bash

ssh_file_dir=/root/workspace/runtime/bin/ssh_dir.sh

sdk_deploy_dir=/root/access/fastaccess_web/version_sdk
dev_sdk_deploy_dir=/root/access/fastaccess_web/dev_sdk
remote_sdk_deploy_dir=/home/fastsdk/deploy_dev_sdk

#包含version_sdk.txt目录
file_name=$1

#将要部署apk路径
apk_file=$2

#部署渠道
channel=$3

#是否为开发模式
dev=$4

check1(){
  if [ $? -ne 0 ];then
   echo "===================$1,执行失败======================="
   exit 1;
  fi
   echo "===================$1,执行成功========================"
}

check(){
	echo "跳过检测"
}

deploy_version_sdk(){
	version_sdk_channel=$1
	version_sdk_file=$2
	sdk_version=$3
	deploy_version_sdk_dir=$sdk_deploy_dir"/"$version_sdk_channel
	if [ ! -d "$deploy_version_sdk_dir" ];then
			mkdir -p $deploy_version_sdk_dir
	fi
	
	deploy_dev_version_sdk_dir=$dev_sdk_deploy_dir"/"$version_sdk_channel
	echo "模式$dev"
	if [ -n $dev ] && [ "$dev" = "dev" ];then
		if [ ! -d "$deploy_dev_version_sdk_dir" ];then
			mkdir -p $deploy_dev_version_sdk_dir
		fi
		deploy_sdk_version_file=$deploy_dev_version_sdk_dir"/"$sdk_version".apk"
	else
		deploy_sdk_version_file=$deploy_version_sdk_dir"/"$sdk_version".apk"
	fi
	
	
	if [ -n $dev ] && [ "$dev" = "dev" ];then
		rsync -avr $version_sdk_file $deploy_sdk_version_file
		check $version_sdk_channel"_"$sdk_version".apk部署到内网开发"
	else
		rsync -avr $version_sdk_file $deploy_sdk_version_file
		check $version_sdk_channel"_"$sdk_version".apk部署到内网生产"
	fi
	
	echo "检测部署服目录"
	sh $ssh_file_dir $channel
	check "检测部署服目录完成"
	
	echo "开始部署版本sdk到部署服"
	rsync -avzr --progress --exclude '.svn' -e'ssh -p 22' $deploy_sdk_version_file  fastsdk@119.29.21.153:"$remote_sdk_deploy_dir/$channel/$sdk_version.apk"
	check $version_sdk_channel"_"$sdk_version".apk部署到部署服"
}

if [ -f $file_name ];then
	file_dir=`echo ${file_name%/*}`
	sed -e 's/.$//' ${file_name}  > ${file_dir}"/version_info.txt"

	read_file=${file_dir}"/version_info.txt"
	
	first_line=$(sed -n '1p' $read_file)
	
	echo "result="$first_line
	
	version=`echo $first_line | awk -F '：' '{print $2}'`
	
	if [ -z "$version" ] || [ "$version" = "" ];then
		version=`echo $first_line | awk -F ':' '{print $2}'`
		if [ -z "$version" ] || [ "$version" = "" ];then
			echo "版本信息配置有误，请重新配置打包"
			#exit 1;
		fi
	fi
	
	echo "version="$version
	echo "开始处理版本空格"
	version=$(echo $version)
	echo "处理后版本：$version"
	
	#发送apk到内网目录
	#发送apk到外网目录
	
	deploy_version_sdk "$channel" "$apk_file" "$version"
	
	basepath=$(cd `dirname $0`; pwd)
	
	sh $basepath/sync_sdk_version_database.sh $channel
fi