#!/bin/bash

source env.sh
source util.sh
source common_env.sh

flag=0
log_name=""
log_date=""
if [ "$2" ];then
   log_date=`echo  $2 | egrep "^([0-9][0-9][0-9][0-9])-(0[1-9]|[1][0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$" `
   if [ !  "$log_date" ];then
     echo "日期格式输入错误，正确格式: yyyy-MM-dd"
     exit 1;
   fi
fi


if [ "$1" ];then 
app=""
for (( i=0; i<${#apps[@]}; i++  ))
do
    attr=(${apps[$i]})
    if [ "$attr" = "$1" ];then
      flag=1
      app=${attr[0]}
      app_type=${attr[1]}
      if [ "$app_type" = "1" ];then
         log_name=$app.log
         if [ "$2" ];then
           log_name=$log_name".$log_date"
         fi
         echo /root/app_logs/$app/$log_name
      elif [ "$app_type" = "2" ];then
         log_name=`date +%Y_%m_%d`.logs
         if [ "$2" ];then
           log_name=${2//\-/_}.logs
         fi
         echo /root/app_logs/$app/$log_name
      else 
        echo "$app没有服务端日志"
        echo ""
        exit 1
      fi
    fi
done
fi

if [ $flag -eq 0 ];then
  echo "输入错误,请输入以下值，全部请输入all"
  for (( i=0; i<${#apps[@]}; i++  ))
  do
    attr=(${apps[$i]})
    echo ${attr[0]}
  done
fi

