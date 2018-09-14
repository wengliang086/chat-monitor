#!/bin/bash

channel=$1
source_dir=$2
target_dir=$3

rsync_sdk() {
	cd $1
	new_file_name=$(ls -lt | head -n2 | sed -n '2p' | awk '{print$9}')
	rsync $new_file_name $target_dir/$2"_fastaccess_sdk.apk"
}

basepath=$(
	cd $(dirname $0)
	pwd
)
pushd $basepath
if [ "$channel" != "all" ]; then
	rsync_sdk $source_dir $channel
else
	cd $source_dir
	channels=$(ls)
	for cn in $channels; do
		rsync_sdk $source_dir/$cn $cn
	done
fi
popd
