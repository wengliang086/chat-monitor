#!/bin/sh

basepath=$(
	cd $(dirname $0)
	pwd
)
cd $basepath

source runtime_bin_util/env.sh
source runtime_bin_util/util.sh

deploy_dir=$4

for (( i = 0; i < ${#apps[@]}; i++ )); do
	app=(${apps[$i]})
	#    cp $process_dir/$app/${app}-1.0-SNAPSHOT.jar $deploy_dir
	echo "app="$app
done

echo "upload 忽略"
