#!/bin/sh
basepath=$(cd `dirname $0`; pwd)
cd $basepath

source runtime_bin_util/util.sh

./build.sh   $*
check "build.sh"
./deploy.sh  $1
check "deploy.sh"
cd $basepath
runtime_bin_util/restart.sh $1 
