#!/bin/sh

svn_url=http://svn.hoolai.com/access-platform/trunk/fast_access/scripts
svn_dir=/root/bin/scripts/

check(){
  if [ $? -ne 0 ];then
   echo "===================$1,执行失败======================="
   exit 1;
  fi
   echo "===================$1,执行成功========================"
}

if [ ! -d $svn_dir ];then
  svn co $svn_url $svn_dir
  check "检出SVN"
else
  svn update $svn_dir
  check "更新SVN"
fi

chmod -R +x scripts

echo "输入参数列表："$@

if [ $# -eq 0 ];then
  echo "必须输入脚本名称"
  exit 1;
fi

./$1 $2 $3 $4 $5 $6 $7 $8





