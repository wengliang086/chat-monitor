#!/bin/sh

find /data/app_logs/ -mtime +90 -name "*.logs" -exec rm -f {} \;
find /data/app_logs/ -mtime +90 -name "*.tar.gz" -exec rm -f {} \;
find /data/app_logs/ -mtime +90 -name "*.log.*" -exec rm -f {} \;
find /data/app_logs/access_open_api/http/ -mtime +2 -name "*.log" -exec rm -f {} \;
find /data/app_logs/nginx/error_logs/ -mtime +90 -name "*.log" -exec rm -f {} \;
find /data/app_logs/nginx/access_logs/ -mtime +90 -name "*.log" -exec rm -f {} \;
find /data/app_logs/dubbo-monitor/logs/ -mtime +10 -name "dubbo-monitor-simple.log.*" -exec rm -f {} \;

