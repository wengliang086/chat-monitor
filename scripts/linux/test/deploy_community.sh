#!/bin/sh

source $WORKSPACE_HOME/runtime/bin/env.sh
source $WORKSPACE_HOME/runtime/bin/util.sh

check(){
    if [ $? -ne 0 ];then
        echo "===================$1,执行失败======================="
        exit 1;
    fi    
    echo "===================$1,执行成功========================"
}

profile="test"
version="";

svn_url="http://svn.hoolai.com/access-platform/trunk/hoolai_game_community/community_open_api"

if [ "$1" = "online" ] || [ "$1" = "online_tx" ]; then 
    echo "community_open_api 线上版本部署开始"
    profile="$1"
    if [ "$2" = ""  ]; then
        echo "请输入要发部的版本号。"
        exit 1
    else
        version=$2
    fi
    svn_url="http://svn.hoolai.com/access-platform/branches/hoolai_game_community/$version/community_open_api"
else 
    echo "community_open_api 主干版本部署开始"
fi 

if [ "$1" = "online" ] || [ "$1" = "online_tx" ];then
if [ ! -d /root/build/community/$profile/$2/community_open_api ];then
  svn co $svn_url /root/build/community/$profile/$2/community_open_api
  check "检出SVN"
else
  svn update /root/build/community/$profile/$2/community_open_api
  check "更新SVN"
fi

mvn -f /root/build/community/$profile/$2/community_open_api/pom.xml -Dprofile=$profile  clean package
cp -R /root/build/community/$profile/$2/community_open_api/target/community_open_api.war /root/deploy/$profile/
else

if [ ! -d /root/build/community/$profile/trunk/community_open_api ];then
  svn co $svn_url /root/build/community/$profile/trunk/community_open_api
  check "检出SVN"
else
  svn update /root/build/community/$profile/trunk/community_open_api
  check "更新SVN"
fi

mvn -f /root/build/community/$profile/trunk/community_open_api/pom.xml -Dprofile=$profile  clean package
cp -R /root/build/community/$profile/trunk/community_open_api/target/community_open_api.war /root/deploy/$profile/
rm -rf /root/workspace/runtime/app/community_open_api
fi





if [ "$1" = "online" ];then
   scp   /root/deploy/$profile/community_open_api.war fastsdk@115.159.118.224:/home/fastsdk/deploy/
   check "上传到所有包正式环境online"
elif [ "$1" = "online_tx" ];then
   scp  /root/deploy/$profile/community_open_api.war fastsdk@119.29.21.153:/home/fastsdk/deploy/
   check "上传到所有包正式环境online_tx"
else 
   unzip /root/deploy/$profile/community_open_api.war -d /root/workspace/runtime/app/community_open_api
   restart_web 10090 community_open_api $WORKSPACE_HOME/runtime/app/community_open_api
fi
