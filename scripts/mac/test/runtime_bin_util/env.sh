#!/bin/sh

XMS_SIZE=256M
XMX_SIZE=256M
XMN_SIZE=128M
PERM_SIZE=128M
MAX_PERM_SIZE=128M

apps=("user_provider 4" "process_provider 4" "open_api 4 20080" "admin 4 20090")
export APP_LOGS=/Users/access/chat_logs
