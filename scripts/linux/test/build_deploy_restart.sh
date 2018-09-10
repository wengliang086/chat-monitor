#!/bin/sh
basepath=$(cd `dirname $0`; pwd)
cd $basepath

source $WORKSPACE_HOME/runtime/bin/util.sh

./build.sh   $*
check "build.sh"
./deploy.sh  $1
check "deploy.sh"
$WORKSPACE_HOME/runtime/bin/restart.sh $1 
