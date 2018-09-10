#!/bin/sh

basepath=$(
	cd $(dirname $0)
	pwd
)
cd $basepath

source runtime_bin_util/env.sh
source runtime_bin_util/util.sh

process_dir=$2
base_project_name=$3
deploy_dir=$4

###1代表provider 2代表web

profile="test"

if [ "$2" = "online_tx" ]; then
	profile="online_tx"
fi

mvn -f $process_dir/$base_project_name/pom.xml -Dprofile=$profile clean deploy
check "mvn构建all"

for (( i = 0; i < ${#apps[@]}; i++)); do
	app=(${apps[$i]})
	check_and_create $deploy_dir
	cp $process_dir/$base_project_name/$app/${app}-1.0-SNAPSHOT.jar $deploy_dir
done
