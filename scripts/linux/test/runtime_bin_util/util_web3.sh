#!/bin/sh

check(){
  if [ $? -ne 0 ];then
   echo "===================$1,执行失败======================="
   exit 1;
  fi
   echo "===================$1,执行成功========================"
}


str_contains(){
 echo "$1" |grep -q "$2"
}


check_and_create(){
if [ ! -d $1 ];then  
  mkdir -p $1
fi

}

check_port_use(){
  COUNT=0
  WHILE_COUNT=0
  MAX_WHILE_COUNT=3
  if [  $2 ]; then
     MAX_WHILE_COUNT=$2
  fi
  echo -e "等待端口$1被占用,等待时间$2秒\c" 
while [  $COUNT -eq 0 ]  &&  [ $WHILE_COUNT -lt $MAX_WHILE_COUNT   ] 
do
  COUNT=`nc -z  -w 1 127.0.0.1 $1|grep succeeded | wc -l`
  if [ $COUNT -gt 0 ];then
     echo -e " 端口$1已被占用"
     return 0
  fi
  let WHILE_COUNT+=1
  echo -e ".\c"
  sleep 1  
done
 
echo -e " 超时,端口$1没有被占用"
start_web 7070 access_web $WORKSPACE_HOME/runtime/app/access_web
}

check_port_release(){
  COUNT=1
  WHILE_COUNT=0
  MAX_WHILE_COUNT=3
  if [  $2 ]; then
    MAX_WHILE_COUNT=$2
  fi
  echo -e "等待端口$1被释放,等待时间$MAX_WHILE_COUNT秒\c" 
while  [ $COUNT -gt 0 ] && [ $WHILE_COUNT -lt $MAX_WHILE_COUNT ] 
do
  COUNT=`nc -z  -w 1 127.0.0.1 $1|grep succeeded | wc -l` 
  if [ $COUNT -eq 0 ];then
    echo " 端口$1被释放"
    return 0
  fi
  let WHILE_COUNT+=1
  echo -e ".\c"
  sleep 1 
done
echo "超时，端口$1正在使用中"
return 1  
}

check_web_port(){
COUNT=0
WHILE_COUNT=0;
echo -e "启动$1\c"
while [ $COUNT -lt 1 ]; do
    if [ $WHILE_COUNT -gt 60 ]; then
       break
    fi   
    let WHILE_COUNT+=1 
    echo -e ".\c"
    sleep 1 
    COUNT=`echo status | nc -i 1 $2 $3 | grep -c HTTP`
    if [ $COUNT -gt 0 ]; then
        echo "启动成功"
        echo ""
	return 0
    fi
done
echo "启动失败"
echo ""
return 1

}

print_line(){
  echo "---------------------------------------------------------------------------------------------------------------"
}

INTERNAL_IP=`/sbin/ifconfig -a|grep inet|grep -v 127.0.0.1|grep -v inet6|awk '{print $2}'|awk -F ":" '{print $2}'`


dump(){

SERVER_NAME=$1
LOGS_DIR=$APP_LOGS

PIDS=`ps -ef | grep java | grep "$SERVER_NAME" |awk '{print $2}'`
if [ -z "$PIDS" ]; then
    echo "ERROR: The $SERVER_NAME does not started!"
    exit 1
fi

if [ ! -d $LOGS_DIR ]; then
	mkdir $LOGS_DIR
fi
DUMP_DIR=$LOGS_DIR/$SERVER_NAME/dump
if [ ! -d $DUMP_DIR ]; then
	mkdir -p $DUMP_DIR
fi
DUMP_DATE=`date +%Y%m%d%H%M%S`
DATE_DIR=$DUMP_DIR/$DUMP_DATE
if [ ! -d $DATE_DIR ]; then
	mkdir $DATE_DIR
fi

echo -e "Dumping the $SERVER_NAME ...\c"
for PID in $PIDS ; do
	jstack $PID > $DATE_DIR/jstack-$PID.dump 2>&1
	echo -e ".\c"
	jinfo $PID > $DATE_DIR/jinfo-$PID.dump 2>&1
	echo -e ".\c"
	jstat -gcutil $PID > $DATE_DIR/jstat-gcutil-$PID.dump 2>&1
	echo -e ".\c"
	jstat -gccapacity $PID > $DATE_DIR/jstat-gccapacity-$PID.dump 2>&1
	echo -e ".\c"
	jmap $PID > $DATE_DIR/jmap-$PID.dump 2>&1
	echo -e ".\c"
	jmap -heap $PID > $DATE_DIR/jmap-heap-$PID.dump 2>&1
	echo -e ".\c"
	jmap -histo $PID > $DATE_DIR/jmap-histo-$PID.dump 2>&1
	echo -e ".\c"
	if [ -r /usr/sbin/lsof ]; then
	/usr/sbin/lsof -p $PID > $DATE_DIR/lsof-$PID.dump
	echo -e ".\c"
	fi
done

if [ -r /bin/netstat ]; then
/bin/netstat -an > $DATE_DIR/netstat.dump 2>&1
echo -e ".\c"
fi
if [ -r /usr/bin/iostat ]; then
/usr/bin/iostat > $DATE_DIR/iostat.dump 2>&1
echo -e ".\c"
fi
if [ -r /usr/bin/mpstat ]; then
/usr/bin/mpstat > $DATE_DIR/mpstat.dump 2>&1
echo -e ".\c"
fi
if [ -r /usr/bin/vmstat ]; then
/usr/bin/vmstat > $DATE_DIR/vmstat.dump 2>&1
echo -e ".\c"
fi
if [ -r /usr/bin/free ]; then
/usr/bin/free -t > $DATE_DIR/free.dump 2>&1
echo -e ".\c"
fi
if [ -r /usr/bin/sar ]; then
/usr/bin/sar > $DATE_DIR/sar.dump 2>&1
echo -e ".\c"
fi
if [ -r /usr/bin/uptime ]; then
/usr/bin/uptime > $DATE_DIR/uptime.dump 2>&1
echo -e ".\c"
fi

echo "OK!"
echo "DUMP: $DATE_DIR"



}

stop_web(){
 let PORT=$1
 let STOP_PORT=$1+1
 STOP_KEY=$2
 let JMX_PORT=$1+2
 JMX_HOST=$INTERNAL_IP
 WEBAPP_NAME=$2
 WEBAPP_PATH=$3 
 check_port_use $STOP_PORT 1
 if [ $? = 0 ]; then
  if [  "$4" != "skip" ]; then
    dump $WEBAPP_NAME
  else
    echo "skip dump" 
  fi
  echo "关闭 $STOP_PORT"
  $JAVA_HOME/bin/java -jar -DSTOP.PORT=$STOP_PORT -DSTOP.KEY=$WEBAPP_NAME  $JETTY_HOME/start.jar --stop
 fi

}

start_web(){
 let PORT=$1
 let STOP_PORT=$1+1
 STOP_KEY=$2
 let JMX_PORT=$1+2
 let JMX_RMI_PORT=$1+3
 JMX_HOST=$INTERNAL_IP
 WEBAPP_NAME=$2
 WEBAPP_PATH=$3
 JDK_OPTS=" -Djava.rmi.server.hostname=$JMX_HOST"
 JDK_OPTS=$JDK_OPTS" -Dcom.sun.management.jmxremote"
 JDK_OPTS=$JDK_OPTS" -Dcom.sun.management.jmxremote.port=$JMX_PORT"
 JDK_OPTS=$JDK_OPTS" -Dcom.sun.management.jmxremote.rmi.port=$JMX_RMI_PORT"
 JDK_OPTS=$JDK_OPTS" -Dcom.sun.management.jmxremote.ssl=false"
 JDK_OPTS=$JDK_OPTS" -Dcom.sun.management.jmxremote.authenticate"
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


 check_port_release $PORT 60
 check "检查端口$PORT释放"
 check_port_release $STOP_PORT 60
 check "检查端口$STOP_PORT释放"
 check_port_release $JMX_PORT 60
 check "检查端口$JMX_PORT 释放"

if [ ! -x $APP_LOGS/$WEBAPP_NAME/http ];then
  mkdir -p $APP_LOGS/$WEBAPP_NAME/http
fi
configfile="--config $JETTY_HOME/etc/jetty.xml"
jetty_session_work_name=""
if [ "$5" ];then
  configfile="--config $JETTY_HOME/etc/jetty-cluster.xml"
  jetty_session_work_name="-Dwork.name=$WEBAPP_NAME"
  cp -rf $JETTY_HOME/etc/jetty-web.xml $WEBAPP_PATH/WEB-INF/
fi

nohup  $JAVA_HOME/bin/java $JDK_OPTS $ENV_OPTS -Dwebapp_name=$WEBAPP_NAME -Djetty.port=$PORT $jetty_session_work_name -jar $JETTY_HOME/jetty-runner.jar  --port $PORT  --stop-port $STOP_PORT   --stop-key $STOP_KEY --log $APP_LOGS/$WEBAPP_NAME/http/"$WEBAPP_NAME"_yyyy_mm_dd.log  --path /$WEBAPP_NAME  $WEBAPP_PATH 2>&1 &

check_web_port "端口$PORT" $INTERNAL_IP $PORT
}
restart_web(){
 stop_web $@
 start_web $@
}
