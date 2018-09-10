#!/bin/sh

apps=("log_provider 1" "access_provider 1" "user_provider 1" "message_service 2" "charge_open_api 2" "access_web 2" "access_open_api 2" "access_admin 2" "community_web 2" "nginx 3")

check(){
  if [ $? -ne 0 ];then
		echo "===================$1,执行失败======================="
	    #exit 1;
  else
		echo "===================$1,执行成功========================"
  fi
}

dozip() {
	cd /data/app_logs/$1
	datestr=$(date -d "10 days ago" +"%Y_%m_%d")
	logfile=$datestr.logs
	zipfile=$datestr.tar.gz
	if [ "$2" = "1" ];then
		datestr=$(date -d "10 days ago" +"%Y-%m-%d")
		logfile=$1.log.$datestr
		zipfile=$1.$datestr.tar.gz
	elif [ "$2" = "3" ];then
		cd access_logs
		datestr=$(date -d "10 days ago" +"%Y-%m-%d")
		logfile=access_$datestr.log
		zipfile=access_$datestr.tar.gz
	fi
	#echo "$1____$logfile"
	if [ ! -f $logfile ];then
		echo "$1 目录的 $logfile 文件不存在"
		return
	fi
	if [ -f $zipfile ];then
		echo "$1 目录的 $zipfile 文件已经存在"
		return
	fi
	
	tar --remove-files -czf $zipfile $logfile
	#tar -czf $zipfile $logfile
	check "$1日志压缩"
}


for (( i=0; i<${#apps[@]}; i++  ))
do
	attr=(${apps[$i]})
	dozip ${attr[0]} ${attr[1]}
done 




