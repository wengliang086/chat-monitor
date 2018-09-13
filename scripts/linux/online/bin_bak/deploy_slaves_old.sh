#!/bin/bash

source $WORKSPACE_HOME/runtime/bin/env.sh
source $WORKSPACE_HOME/runtime/bin/util.sh
source /data/bin/common_env.sh
source $WORKSPACE_HOME/runtime/bin/common_env.sh

flag=1

nginx_switch_restart(){
   group=$1
   if [ "$group" = "0"  ];then
     group=""
     sed -i "s/app_proxy_pass\/group.*\//app_proxy_pass\/group\//g"  /usr/local/services/nginx/conf/nginx.conf
   else
     sed -i "s/app_proxy_pass\/group.*\//app_proxy_pass\/group$group\//g"  /usr/local/services/nginx/conf/nginx.conf
   fi
   /usr/local/services/nginx/sbin/nginx -s reload
   for (( n=0; n<${#ips[@]}; n++  ))
   do
     nginx_ip=(${ips[$n]})
     nginx_ip=(${nginx_ip[0]})
     echo "nginx远程重启$nginx_ip"
     ./rsync_nginx.sh $nginx_ip
   done
}




flag=1
if [ $1 ];then
echo "ips $ips"
for (( m=0; m<${#ips[@]}; m++  ))
do
ip=(${ips[$m]})
group=(${ip[1]})
ip=(${ip[0]})

group_switch="2"

if [ "$group" = "2" ];then
  group_switch="1"
fi

if [ ! "$2" = "skip_deploy"  ];then
rsync -avzr --progress --delete --exclude '.svn' -e'ssh -p 22'  /root/.bash_profile  root@$ip:/root/.bash_profile
rsync -avzr --progress --delete --exclude '.svn' -e'ssh -p 22'  /data/workspace/     root@$ip:/data/workspace/
fi
nginx_is_switch=0
for (( i=0; i<${#apps[@]}; i++  ))
do
    attr=(${apps[$i]})
    app=${attr[0]}
    type=${attr[1]}
    if [ ${attr[0]} = $1 ] || [ $1 = "all" ];then
        flag=0
        if [ "$type" = "2" ];then
         if [ "$nginx_is_switch" -eq 0 ];then
           nginx_is_switch=1
           nginx_switch_restart $group_switch
           check "切换nginx到group"$group_switch
           sleep 2s
         fi
         ./shell_remote.sh  $ip  "source /root/.bash_profile && /data/workspace/runtime/bin/restart.sh ${attr[0]}" 
        elif [ "$type" = "3" ];then
          echo "packing_client_swt部署成功"
        else    
          ./shell_remote.sh  $ip  "source /root/.bash_profile && /data/workspace/runtime/bin/restart.sh ${attr[0]}"
        fi
    fi
done

if [ "$nginx_is_switch" -eq 1  ];then
  sleep 5s
  nginx_switch_restart 0
  check "恢复nginx" 
  sleep 5s
fi

echo "----------------------------------------------------------------------"
done
fi

if [ "$flag" -eq 1 ];then
  echo "输入错误,请输入以下值，全部请输入all"
  for (( i=0; i<${#apps[@]}; i++  ))
  do
    attr=(${apps[$i]})
    echo ${attr[0]}
  done
fi
