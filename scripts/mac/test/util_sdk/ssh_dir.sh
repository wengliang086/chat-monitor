#!/bin/bash

dir=/home/fastsdk/deploy_dev_sdk

echo "params=$1"

ssh fastsdk@119.29.21.153 >/dev/null 2>&1 <<eeooff

echo "kkkkk"

if [ ! -d "$dir/$1" ];then
	mkdir -p "$dir/$1"
fi

eeooff
