#!/bin/bash

tools_dir=/data/workspace/common/tools



path=$1
app=$2

if [ ! $path ] || [ "" = $path ];then
	echo "路径path=$pat,h传入有误"
	exit 1
fi

if [ "packing_client_swt" != $app ];then
	echo "部署app名有误，请检查后重试!!"
	exit 1
fi

if [ -f $path/"packing_client_swt_win.zip" ];then
	rm -rf $path/"packing_client_swt_win.zip"
fi

if [ -f $path/"packing_client_swt_mac.zip" ];then
	rm -rf $path/"packing_client_swt_mac.zip"
fi

if [ -f $path/"packing_swt.zip" ];then
	rm -rf $path/"packing_swt.zip"
fi

if [ ! -d $path ];then
	echo "创建目录$path"
	mkdir -p $path
fi

if [ -d $path/$app"_win" ];then
	echo "删除目录"$path/$app"_win"
	rm -rf $path/$app"_win"
fi


if [ -d $path/$app"_mac" ];then
	echo "删除目录"$path/$app"_mac"
	rm -rf $path/$app"_mac"
fi

if [ -d $path/"packing_swt" ];then
	echo "=====删除目录"$path/"packing_swt"
	rm -rf $path/"packing_swt"
fi

mkdir -p $path/$app"_win"
mkdir -p $path/$app"_mac"

cp -r $path/$app/* $path/$app"_win"
cp -r $path/$app/* $path/$app"_mac"

if [ -d $path/$app"_win/tools/android/mac" ];then
	echo "删除目录"$path/$app"_win/tools/android/mac"
	rm -rf $path/$app"_win/tools/android/mac" 
fi

if [ -d $path/$app"_win/tools/java/mac" ];then
	echo "删除目录"$path/$app"_win/tools/java/mac"
	rm -rf $path/$app"_win/tools/java/mac" 
fi

if [ -d $path/$app"_mac/tools/android/win" ];then
	echo "删除目录"$path/$app"_mac/tools/android/win"
	rm -rf $path/$app"_mac/tools/android/win" 
fi

if [ -d $path/$app"_mac/tools/java/win" ];then
	echo "删除目录"$path/$app"_mac/tools/java/win"
	rm -rf $path/$app"_mac/tools/java/win" 
fi

cur_dir=$(pwd)

tools_zip() {
	type=$1
	pushd $cur_dir

	cd $path/$app"_"$type

	zip -r $type"_tools".zip ./tools

	mv $type"_tools".zip $tools_dir

	popd
}

tools_zip "win"
tools_zip "mac"

pushd $cur_dir

cd $path

echo "开始压缩目录"$path/$app"_win"
zip -r $app"_win".zip ./$app"_win"

echo "开始压缩目录"$path/$app"_mac"
zip -r $app"_mac".zip ./$app"_mac"

popd

pushd $cur_dir

cd $path

if [ -d $path/"packing_swt/packing_client_swt" ];then
	rm -rf $path/"packing_swt/packing_client_swt"
fi

mkdir -p $path/"packing_swt/packing_client_swt"
cp -r $path/$app/* $path/"packing_swt/packing_client_swt"

rm -rf $path/"packing_swt/packing_client_swt"/tools

cd "packing_swt"

zip -r "packing_swt.zip" "./packing_client_swt"

mv "packing_swt.zip" ../

popd
