#!/bin/sh

basepath=$(
	cd $(dirname $0)
	pwd
)
cd $basepath

source env.sh
source util.sh

#新版本管理台部署 参数（1、profile；2、项目名称）

SERVER_PORT=$1
project_name=$2
deploy_dir=$3
profile="test"

stop() {
	SERVER_NAME=$1
	PIDS=$(ps -ef | grep java | grep '\-\-spring.profiles' | grep "$SERVER_NAME" | awk '{print $2}')
	if [ -z "$PIDS" ]; then
		echo "ERROR: The $SERVER_NAME does not started!"
		#        exit 1
		return 0
	fi
	echo -e "Stopping the $SERVER_NAME ...\c"
	for PID in $PIDS; do
		kill $PID >/dev/null 2>&1
	done

	COUNT=0
	while [ $COUNT -lt 1 ];
	do
		echo -e ".\c"
		sleep 1
		COUNT=1
		for PID in $PIDS;
		do
			PID_EXIST=$(ps -f -p $PID | grep java)
			if [ -n "$PID_EXIST" ]; then
				COUNT=0
				break
			fi
		done
	done

	echo "OK!"
	echo "PID: $PIDS"
}

echo "关闭 $project_name"
stop $project_name
echo "启动 $project_name"

if [ ! -d $APP_LOGS/$project_name ]; then
    mkdir -p $APP_LOGS/$project_name
fi
STDOUT_FILE=$APP_LOGS/$project_name/stdout.log
nohup java -jar ${deploy_dir}/${project_name}-1.0-SNAPSHOT.jar --spring.profiles.active=$profile >${STDOUT_FILE} 2>&1 &

COUNT=0
while [ $COUNT -lt 1 ]; do
	echo -e ".\c"
	sleep 1
	COUNT=`ps -ef | grep java | grep '\-\-spring.profiles' | grep "$project_name" | awk '{print $2}' | wc -l`
	#COUNT=$(netstat -an | grep $SERVER_PORT | wc -l)
	if [ $COUNT -gt 0 ]; then
		break
	fi
done

echo "OK!"
PIDS=$(ps -ef | grep java | grep '\-\-spring.profiles' | grep "$project_name" | awk '{print $2}')
echo "PID: $PIDS"
