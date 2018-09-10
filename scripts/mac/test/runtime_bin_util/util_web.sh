#!/bin/sh

PORT=$1
WEBAPP_NAME=$2
WEBAPP_PATH=$3

INTERNAL_IP=$(/sbin/ifconfig -a | grep inet | grep -v 127.0.0.1 | grep -v inet6 | awk '{print $2}' | awk -F ":" '{print $2}')

check_port_use() {
	COUNT=0
	WHILE_COUNT=0
	MAX_WHILE_COUNT=3
	if [ $2 ]; then
		MAX_WHILE_COUNT=$2
	fi
	echo -e "等待端口$1被占用,等待时间$2秒\c"
	while [ $COUNT -eq 0 ] && [ $WHILE_COUNT -lt $MAX_WHILE_COUNT ]; do
		COUNT=$(nc -z -w 1 127.0.0.1 $1 | grep succeeded | wc -l)
		if [ $COUNT -gt 0 ]; then
			echo -e " 端口$1已被占用"
			return 0
		fi
		let WHILE_COUNT+=1
		echo -e ".\c"
		sleep 1
	done

	echo -e " 超时,端口$1没有被占用"
	return 1
}

stop_web() {
	let PORT=$1
	WEBAPP_NAME=$2
	check_port_use $PORT 1
	if [ $? = 0 ]; then
		$JAVA_HOME/bin/java -jar -DSTOP.PORT=$PORT -DSTOP.KEY=$WEBAPP_NAME /root/workspace/common/jetty-9.2.10/start.jar --stop
	fi
}

start_web() {
	let PORT=$1
	JMX_HOST=$INTERNAL_IP
	WEBAPP_NAME=$2
	WEBAPP_PATH=$3
	JDK_OPTS=$JDK_OPTS" -Xms$XMS_SIZE -Xmx$XMX_SIZE -Xmn$XMN_SIZE -Xss1m -XX:PermSize=$PERM_SIZE -XX:MaxPermSize=$MAX_PERM_SIZE"
	#Eden区与Survivor区的大小比值
	JDK_OPTS=$JDK_OPTS" -XX:SurvivorRatio=1"
	#设置年轻代为并行收集
	JDK_OPTS=$JDK_OPTS" -XX:+UseParNewGC"
	#使用CMS内存收集
	JDK_OPTS=$JDK_OPTS" -XX:+UseConcMarkSweepGC"
	#多少次后进行内存压缩
	JDK_OPTS=$JDK_OPTS" -XX:CMSFullGCsBeforeCompaction=5"
	#在FULL GC的时候, 对年老代压缩
	JDK_OPTS=$JDK_OPTS" -XX:+UseCMSCompactAtFullCollection"
	#设置垃圾回收时间占程序运行的时间百分比 1/(1 + n)
	JDK_OPTS=$JDK_OPTS" -XX:GCTimeRatio=19"
	#禁用类垃圾回收
	#JDK_OPTS=$JDK_OPTS" -Xnoclassgc"
	#多少百分比后开始使用cms收集作为垃圾回收, 满足(Xmx - Xmn) * (100 - CMSInitiatingOccupancyFraction) / 100 >= Xmn
	JDK_OPTS=$JDK_OPTS" -XX:CMSInitiatingOccupancyFraction=90"
	#每兆堆空闲空间中SoftReference的存活时间
	JDK_OPTS=$JDK_OPTS" -XX:SoftRefLRUPolicyMSPerMB=0"
	#JDK_OPTS=$JDK_OPTS" -XX:MaxTenuringThreshold=31"
	JDK_OPTS=$JDK_OPTS" -XX:+DisableExplicitGC"

	ENV_OPTS="-Dorg.apache.jasper.compiler.disablejsr199=true"

	if [ ! -x $APP_LOGS/$WEBAPP_NAME/http ]; then
		mkdir -p $APP_LOGS/$WEBAPP_NAME/http
	fi

	nohup $JAVA_HOME/bin/java $JDK_OPTS $ENV_OPTS -Dwebapp_name=$WEBAPP_NAME -Djetty.port=$PORT -jar /root/workspace/common/jetty-9.2.10/jetty-runner-8.1.9.jar --lib /root/workspace/common/jetty-9.2.10/lib --port $PORT --log $APP_LOGS/$WEBAPP_NAME/http/"$WEBAPP_NAME"_yyyy_mm_dd.log --path /$WEBAPP_NAME $WEBAPP_PATH 2>&1 | rotatelogs $APP_LOGS/$WEBAPP_NAME/$WEBAPP_NAME_%Y_%m_%d.logs 86400 &
}
restart_web() {
	stop_web $@
	start_web $@
}
