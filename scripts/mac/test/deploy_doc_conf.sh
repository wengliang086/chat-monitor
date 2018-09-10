#!/bin/sh
basepath=$(cd `dirname $0`; pwd)
cd $basepath

source runtime_bin_util/util.sh

echo $basepath
SERVER_PORT=3000
build_dir=/Users/access/workspace/build/
svn_url=http://svn.hoolai.com/access-platform/trunk/docs

echo $build_dir$1
if [ "" == "$1" ] ; then
	echo "在命令后需要加上docs"
	exit 1;
else 
	if [ "docs" == "$1" ] ; then
		echo "$build_dir$1/"
		if [ -d = "$build_dir$1/" ] ; then
			svn co $svn_url -r head $build_dir$1
			echo "test--------------检出"
			check "检出SVN"
		else
			echo "test--------------更新"
			pwd
			svn update $build_dir$1
			check "更新SVN"
		fi
		cd $build_dir
		docsify serve docs &
		echo "启动成功！"
		exit;
	fi
fi