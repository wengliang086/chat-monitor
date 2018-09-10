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

if [ "$1" = "online_tx" ];then
  profile="online_tx"
fi

echo $profile


svn_url="http://svn.hoolai.com/access-platform/trunk/hoolai_game_custom/access_custom"

echo "access_custom 版本部署开始"


if [ ! -d /root/build/access_custom/$profile/access_custom ];then
  svn co $svn_url /root/build/access_custom/$profile/access_custom
  check "检出SVN"
else
  svn update /root/build/access_custom/$profile/access_custom
  check "更新SVN"
fi

mvn -f /root/build/access_custom/$profile/access_custom/pom.xml  -P$profile clean package
cp -R /root/build/access_custom/$profile/access_custom/target/access_custom.war /root/deploy/$profile/


if [ "$1" = "online_tx" ];then
   scp   /root/deploy/$profile/access_custom.war fastsdk@119.29.21.153:/home/fastsdk/deploy/
   check "上传到所有包正式环境online"
else 
   rm -rf /root/workspace/runtime/app/access_custom/*
   unzip /root/deploy/$profile/access_custom.war -d /root/workspace/runtime/app/access_custom
   restart_web 10100 access_custom $WORKSPACE_HOME/runtime/app/access_custom
fi
