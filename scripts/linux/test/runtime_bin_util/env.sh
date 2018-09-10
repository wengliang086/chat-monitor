#!/bin/sh




XMS_SIZE=256M
XMX_SIZE=256M
XMN_SIZE=128M 
PERM_SIZE=128M
MAX_PERM_SIZE=128M


#apps=("log_provider 1" "access_provider 1" "user_provider 1" "query_provider 1"  "message_service 2 10020" "charge_open_api 2 8080" "access_web 2 7070" "access_open_api 2 9090" "access_admin 2 10030" "client_log 2 10070")
apps=("log_provider 1" "access_provider 1" "user_provider 1" "query_provider 1"  "message_service 2 10020" "charge_open_api 2 8080" "access_web 2 7070" "access_open_api 2 9090" "access_admin 2 10030" "packing_client_swt 3" "client_log 2 10070")
export APP_LOGS=/root/app_logs


