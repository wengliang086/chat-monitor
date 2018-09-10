#!/bin/sh

sdk_deploy_dir=/root/access/fastaccess_web/sdk

check(){
  if [ $? -ne 0 ];then
    echo "===================$1,执行失败======================="
    exit 1;
  fi
    echo "===================$1,执行成功========================"
}

uploadOnline(){
   rsync -avzr --progress --exclude '.svn' -e'ssh -p 22' $sdk_deploy_dir/$1_fastaccess_sdk.apk  fastsdk@119.29.21.153:/home/fastsdk/deploy_sdk/
   
   rsync -avzr --progress --exclude '.svn' -e'ssh -p 22' $sdk_deploy_dir/$1_fastaccess_sdk.apk  access@192.168.150.158:/Users/access/Documents/access_sdk
}

allHoolaiChannels=("hoolai" "hoolaipay" "hoolaiysdk") 

if [ "" == "$1" ] ; then
    echo "在命令后面加一个渠道 或者 all（注意，只包含hoolai相关渠道）"
	for (( i=0; i<${#allHoolaiChannels[@]}; i++  ))
	do
		echo "${allHoolaiChannels[$i]}"
	done
    exit 1;
else
    if [ "all" = "$1" ] ; then
		for (( i=0; i<${#allHoolaiChannels[@]}; i++  ))
		do
			hoolaiChannel=(${allHoolaiChannels[$i]})
			if [[ $hoolaiChannel =~ "hoolai" ]] ; then
				echo "开始上传$hoolaiChannel"
				uploadOnline $hoolaiChannel
				check $hoolaiChannel
			else
				echo "跳过：$hoolaiChannel"
			fi
		done
    else    
        uploadOnline $1
		check $1
    fi
fi

