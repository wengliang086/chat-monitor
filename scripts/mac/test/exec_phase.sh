#!/bin/sh

basePath=$(
	cd $(dirname $0)
	pwd
)
#echo $basePath

if [ ! $1 ]; then
    echo "\033[0;31m 未输入操作名 \033[0m  \033[0;34m {start|stop|restart|status} \033[0m"
fi

function start() {
    echo "do start"
}

function stop() {
    echo "do stop"
}

function restart() {
    stop
    sleep 1
    start
}

function status() {
    echo "do status"
}

case $1 in
        start) start;;
        stop) stop;;
        restart) restart;;
        status) status;;
        *) echo -e "\033[0;31m Usage: \033[0m  \033[0;34m sh  $0  {start|stop|restart|status}  {SpringBootJarName} \033[0m
                  \033[0;31m Example: \033[0m  \033[0;33m sh  $0  start esmart-test.jar \033[0m"
esac

