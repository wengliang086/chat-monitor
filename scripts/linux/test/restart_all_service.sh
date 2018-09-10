#!/bin/bash

PATH=/bin:/sbin:/usr/bin:/usr/sbin:/usr/local/bin:/usr/local/sbin:~/bin:/root/.gradle/wrapper/dists/gradle-2.2.1-all/6dibv5rcnnqlfbq9klf8imrndn/gradle-2.2.1/bin:/root/bin:/root/workspace/common/jdk1.7.0_60/bin:/root/workspace/common/jdk1.7.0_60/jre/bin:/root/workspace/common/android-sdk-linux/build-tools/20.0.0:/root/common_modules/apache-maven-2.2.1/bin:/root/common_modules/apache-ant-1.9.3/bin

export PATH

cd /root/bin

#./jenkins.sh stop
#./jenkins.sh start
 
#cd /root/tools/sonarqube-4.5.4/bin/linux-x86-64/
 
#./sonar.sh start

cd /root/common_modules/redis-2.8.13/src

./redis_start.sh

rm -rf /root/bin/data/zookeeper_server.pid

cd /root/common_modules/zookeeper-3.4.6/bin/

 ./zkServer.sh start >> /root/bin/zookeeper.log

cd /root/common_modules/dubbo-monitor-simple-2.5.3/bin/
./restart.sh >> /root/bin/dubbo-monitor.log

cd /root/common_modules/dubbo-admin-tomcat-6/bin/
./startup.sh >> /root/bin/dubbo-admin-tomcat.log

sh  /root/workspace/runtime/bin/restart.sh all  >> /root/bin/runtime.log

