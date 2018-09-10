#!/bin/sh
export sdk_build_dir=$WORKSPACE_HOME/build/fast_access_sdk_dir_studio
echo "=================== 检查渠道sdk有armeabi却没有armeabi-v7a的情况 ========================"
armeabi_dir=/app/src/main/jniLibs/armeabi
armeabi_v7a_dir=/app/src/main/jniLibs/armeabi-v7a
if [ -d "$sdk_build_dir/$1/$armeabi_dir" ]; then
	ishavearme="Y"
	echo "是否含有armeabi："$ishavearme
else
	ishavearme="N"
	echo "是否含有armeabi："$ishavearme
fi

if [ -d "$sdk_build_dir/$1/$armeabi_v7a_dir" ]; then
	ishavearmeV7a="Y"
	echo "是否含有armeabi-v7a："$ishavearmeV7a
else
	ishavearmeV7a="N"
	echo "是否含有armeabi-v7a："$ishavearmeV7a
fi

if [ "$ishavearme" == "$ishavearmeV7a" ]; then
	echo "===================armeabi,检测成功======================="
else
	echo "===================armeabi,检测失败======================="
	exit 1
fi
