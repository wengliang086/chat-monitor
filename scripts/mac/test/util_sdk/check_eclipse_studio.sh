#!/bin/sh

#读eclipse文件并且把渠道放入channels数组
lines=0
while read -a ARRAY
do
	aaa=(${ARRAY[@]})
	if [ $aaa ];then
		channels_eclipse[lines]=${aaa[0]}
	fi
	let "lines = $lines + 1"
done < ../sdk/conf/channel.txt
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
done < ../studio/conf/channel.txt
#echo "studio渠道列表"
#echo ${channels_studio[*]}

echo "没有重复渠道"



