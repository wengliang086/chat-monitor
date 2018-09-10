#!/bin/sh

sdk_build_dir_studio=/root/build/fast_access_sdk_dir_studio
sdk_build_dir_eclipse=/root/build/fast_access_sdk_dir

sdk_command_config_dir_studio=$sdk_build_dir_studio/zzz_conf
sdk_command_config_dir_eclipse=$sdk_build_dir_eclipse/zzz_conf

svn_studio=http://svn.hoolai.com/access-platform/trunk/fast_access_sdk_AndroidStudio
svn_eclipse=http://svn.hoolai.com/access-platform/trunk/fast_access_sdk

#更新配置文件
rm -rf $sdk_command_config_dir_eclipse
rm -rf $sdk_command_config_dir_studio
svn co $svn_eclipse/zzz_conf -r head $sdk_command_config_dir_eclipse
svn co $svn_studio/zzz_conf -r head $sdk_command_config_dir_studio

#读eclipse文件并且把渠道放入channels数组
lines=0
while read -a ARRAY
do
	aaa=(${ARRAY[@]})
	if [ $aaa ];then
		channels_eclipse[lines]=${aaa[0]}
	fi
	let "lines = $lines + 1"
done < $sdk_command_config_dir_eclipse/channel.txt
#echo "eclipse渠道列表"
#echo ${channels_eclipse[*]}

checkParas(){
	tag="false"
	for((k=0;k<${#channels_eclipse[@]};k++))
		do
			if [ ${channels_eclipse[k]} = $1 ]; then
				tag="true"
				break
			fi
		done
	if [ "$tag" = "true" ] ; then
		echo "渠道 $1 重复"
		exit 1;
	fi
}

#读studio文件检查重复
lines=0
while read -a ARRAY
do
	bbb=(${ARRAY[@]})
	if [ $bbb ];then
		channels_studio[lines]=${bbb[0]}
		checkParas ${bbb[0]}
	fi
	let "lines = $lines + 1"
done < $sdk_command_config_dir_studio/channel.txt
#echo "studio渠道列表"
#echo ${channels_studio[*]}

echo "没有重复渠道"



