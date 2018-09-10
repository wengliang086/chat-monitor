#!/bin/sh

basepath=$(cd `dirname $0`; pwd)
cd $basepath

#新版本管理台部署 参数（1、profile；2、分支部署版本）

build_dir=/Users/access/workspace/build
deploy_dir=/Users/access/workspace/deploy

SERVER_PORT=8088
profile="test"
svn_base=http://svn.hoolai.com/access-platform
sub_project=/open/access_management2
svn_trunk=$svn_base/trunk/fast_access$sub_project
svn_branches=$svn_base/branches/fast_access

check(){
  if [ $? -ne 0 ];then
   echo "===================$1,执行失败======================="
   exit 1;
  fi
   echo "===================$1,执行成功========================"
}

#参数合法性验证
params_count=$#
branche_version=""
if [ $params_count -ge 1 ]; then
    profile=$1
    if [ $profile != "test" ] && [ $profile != "online" ]; then
        echo "profile必须是test或者online"
        exit 1;
    fi
    if [ $profile = "online" ]; then
        if [ $params_count -ge 2 ]; then
            branche_version=$2
            max_version=`svn ls $svn_branches | tail -n 1 | awk '{print substr($0,1,6)}'`
            echo "当前分支最大版本号为：$max_version"
            if [ $branche_version != $max_version ];then
                echo "版本号输入错误！不是最新分支版本"
                exit 1;
            fi
        else
            echo "分支部署必须输入版本号"
            exit 1;
        fi
    fi
fi

#SVN检出
svn_url=$svn_trunk
svn_dir=$build_dir/$profile/trunk/fast_access$sub_project
if [ $profile = "test" ]; then
    echo "主干版本部署开始"
else
    echo "分支版本部署开始"
    svn_url=$svn_branches/$branche_version$sub_project
    svn_dir=$build_dir/$profile/branches/fast_access$sub_project
fi

if [ -d $svn_dir ];then
  svn co $svn_url  $svn_dir
  check "检出SVN"
else
  svn update $svn_dir
  check "更新SVN"
fi

#构建
project_name="access_mgr_admin"
echo $svn_dir
#使用jdk1.8
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_91.jdk/Contents/Home
mvn -f $svn_dir/access_mgr_parent/pom.xml clean install -Dmaven.test.skip=true
check "$project_name mvn 构建"

target_app_name=$project_name-1.0.0-SNAPSHOT.jar
target_app_name_path=$svn_dir/$project_name/target/$target_app_name
current_deploy_dir=$deploy_dir/$profile
cp -R  $target_app_name_path  $current_deploy_dir/
check  复制"$target_app_name_path"到"$current_deploy_dir"目录
if [ "$profile" = "online" ];then
  scp  $deploy_dir/$profile/$target_app_name fastsdk@119.29.21.153:/home/fastsdk/deploy/
  check "上传到正式环境"
fi
mvn -f $svn_dir/access_mgr_parent/pom.xml clean

stop() {
    SERVER_NAME=$1
    PIDS=`ps -ef | grep java | grep "$SERVER_NAME" |awk '{print $2}'`
    if [ -z "$PIDS" ]; then
        echo "ERROR: The $SERVER_NAME does not started!"
#        exit 1
        return 0
    fi
    echo -e "Stopping the $SERVER_NAME ...\c"
    for PID in $PIDS ; do
        kill $PID > /dev/null 2>&1
    done

    COUNT=0
    while [ $COUNT -lt 1 ]; do
        echo -e ".\c"
        sleep 1
        COUNT=1
        for PID in $PIDS ; do
            PID_EXIST=`ps -f -p $PID | grep java`
            if [ -n "$PID_EXIST" ]; then
                COUNT=0
                break
            fi
        done
    done

    echo "OK!"
    echo "PID: $PIDS"
}

stop $project_name
echo "启动 $project_name"
cd $current_deploy_dir
nohup java -jar access_mgr_admin-1.0.0-SNAPSHOT.jar --spring.profiles.active=test > /dev/null 2>&1 &

COUNT=0
while [ $COUNT -lt 1 ]; do
    echo -e ".\c"
    sleep 1
#    COUNT=`ps -f | grep java | grep "$project_name" | awk '{print $2}' | wc -l`
    COUNT=`netstat -an | grep $SERVER_PORT | wc -l`
    if [ $COUNT -gt 0 ]; then
        break
    fi
done

echo "OK!"
PIDS=`ps -ef | grep java | grep "$project_name" | awk '{print $2}'`
echo "PID: $PIDS"
