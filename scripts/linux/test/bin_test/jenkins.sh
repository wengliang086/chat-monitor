#! /bin/sh

#启动方法
start(){     
   # nohup java  -jar /root/workspace/common/jenkins.war  --httpPort=9001 > /root/app_logs/jenkins/jenkins_yyyy_mm_dd.log 2>&1 &   
   # java -Dhudson.util.ProcessTree.disable=true  -jar /root/workspace/common/jenkins.war  --httpPort=9001 >> /root/app_logs/jenkins/jenkins_`%Y-%m-%d`.log  &
    java  -jar /root/workspace/common/jenkins.war -Dhudson.util.ProcessTree.disable=true  --httpPort=9001 >> /root/app_logs/jenkins/jenkins_`%Y-%m-%d`.log  &  
}

#停止方法
stop(){    
    ps -ef|grep jenkins|awk '{print $2}'|while read pid 
    do        
        kill -9 $pid    
    done
}
case "$1" in  
  start)        start;;  
  stop)        stop;; *) 
printf '
Usage: %s {start|stop}\n'
 "$prog"exit 1;;
esac     

