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
	if [ "all" = "$1" ] || [[ "$1" =~ "hoolai" ]] ; then
        echo "函数内部渠道不能是all或者hoolai相关"
		exit 1;
    elif [ "$1" = "assets" ] || [ "$1" = "manifest" ] || [ "$1" = "smali" ] || [ "$1" = "application" ] || [ "$1" = "res" ];then
		rsync -avzr --progress --exclude '.svn' -e'ssh -p 22' $sdk_deploy_dir/$1  fastsdk@119.29.21.153:/home/fastsdk/deploy_sdk/
        rsync -avzr --progress --exclude '.svn' -e'ssh -p 22' $sdk_deploy_dir/$1.zip  fastsdk@119.29.21.153:/home/fastsdk/deploy_sdk/
		
		rsync -avzr --progress --exclude '.svn' -e'ssh -p 22' $sdk_deploy_dir/$1  access@192.168.150.158:/Users/access/Documents/access_sdk
		rsync -avzr --progress --exclude '.svn' -e'ssh -p 22' $sdk_deploy_dir/$1.zip  access@192.168.150.158:/Users/access/Documents/access_sdk
    else    
        rsync -avzr --progress --exclude '.svn' -e'ssh -p 22' $sdk_deploy_dir/$1_fastaccess_sdk.apk  fastsdk@119.29.21.153:/home/fastsdk/deploy_sdk/
		
		rsync -avzr --progress --exclude '.svn' -e'ssh -p 22' $sdk_deploy_dir/$1_fastaccess_sdk.apk  access@192.168.150.158:/Users/access/Documents/access_sdk
    fi
}


if [ "" == "$1" ] ; then
    echo "在命令后面加一个渠道 或者 all（注意，不会包含hoolai相关渠道）"
    exit 1;
else
    if [ "all" = "$1" ] ; then
		sdk_command_config_dir=$sdk_build_dir/zzz_conf
		svn=http://svn.hoolai.com/access-platform/trunk/fast_access_sdk_AndroidStudio

		rm -rf $sdk_command_config_dir
		svn co $svn/zzz_conf -r head $sdk_command_config_dir
	
		lines=0
		while read -a ARRAY
		do
			aaa=(${ARRAY[@]})
			if [ $aaa ];then
				if [[ "${aaa[0]}" =~ "hoolai" ]] ; then
					echo "跳过：${aaa[0]}"
				else
					uploadOnline ${aaa[0]}
					check ${aaa[0]}
				fi
			fi
			let "lines = $lines + 1"
		done < $sdk_command_config_dir/channel.txt
    elif [[ "$1" =~ "hoolai" ]] ;then
        echo "渠道不能是hoolai相关"
		exit 1;
    else    
        uploadOnline $1
		check $1
    fi
fi



