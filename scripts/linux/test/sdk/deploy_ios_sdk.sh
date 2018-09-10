#!bin/sh

deploy_sdk_dir=/root/access/fastaccess_web/sdk
source $WORKSPACE_HOME/runtime/bin/env_ios.sh

check_resource(){
    for ((i=0; i<${#RESOURCE_ARRAY[@]}; i++ ));
    do
        attr=(${RESOURCE_ARRAY[$i]})
        if [ "$1" = "${attr[0]}" ];then
            return 0
        fi
    done
    return 1
}

check_channel(){
    if [ "$1" = "all" ];then
        return 0
    else
        for ((i=0; i<${#CHANNEL_ARRAY[@]}; i++ ));
        do
            attr=(${CHANNEL_ARRAY[$i]})
            if [ "$1" = "${attr[0]}" ];then
                return 0
            fi
        done
    fi
    return 1
}

if [ ! $1 ] || [ "$1" = "" ];then
	echo "请输入渠道参数！"
	exit 1;
fi

echo "==========检查渠道参数 $1=========="
check_channel $1
if [ $? -ne 0 ];then
	echo "渠道$1输入错误，请重新输入"
	exit 1;
fi

echo "==========渠道参数输入正确=========="

if [ "$1" = "all" ];then
	for file in $deploy_sdk_dir/*
	do
		if test -f $file
		then
			file_str=`basename $file` 
			file_type=${file_str##*.}
			if [ $file_type ] && [ "$file_type" = "zip" ];then
				file_name=${file_str%.*}
				if [ "$2_ios_sdk.zip" == "$file_name.$file_type" ];then
					echo "忽略$2.$file_type文件"
					continue;
				fi
				check_resource $file_name
				if [ $? -eq 1 ];then
					echo "==========开始上传IOS_SDK--- $file_name.$file_type=========="
					rsync -avzr --progress --exclude '.svn' -e'ssh -p 22' $file  $DEPLOY_USER@$DEPLOY_SERVER_IP:$DEPLOY_IOS_SDK
					echo "==========$file_name.$file_type 上传完成=========="
				fi
			fi
		fi
	done
else
	file_path=$deploy_sdk_dir/"$1_ios_sdk.zip"
	if [ ! -f $file_path ];then
		echo "==========文件$1_ios_sdk.zip不存在，请重新上传！=========="
		exit 0;
	fi
	echo "==========开始上传sdk $1_ios_sdk.zip ========="
	echo "==========文件路径：$file_path =========="

	rsync -avzr --progress --exclude '.svn' -e'ssh -p 22' $file_path  $DEPLOY_USER@$DEPLOY_SERVER_IP:$DEPLOY_IOS_SDK
	echo "==========$1_ios_sdk.zip 上传完成 =========="
fi
