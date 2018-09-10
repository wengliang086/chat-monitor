#!/bin/sh

export sdk_build_dir=/root/build/fast_access_sdk_dir_studio
sdk_command_config_dir=$sdk_build_dir/zzz_conf
export sdk_deploy_dir=/root/access/fastaccess_web/sdk
svn=http://svn.hoolai.com/access-platform/trunk/fast_access_sdk_AndroidStudio

#更新配置文件
#rm -rf $sdk_command_config_dir
#svn co $svn/zzz_conf -r head $sdk_command_config_dir

sh ../sdk/check_eclipse_studio.sh


#读文件并且把渠道放入channels数组
lines=0
while read -a ARRAY
do
	aaa=(${ARRAY[@]})
	if [ $aaa ];then
		channels[lines]=${aaa[0]}
	fi
	let "lines = $lines + 1"
done < $sdk_command_config_dir/channel.txt
echo "可输入参数列表"
echo ${channels[*]}

checkParas(){
	tag="false"
	for((k=0;k<${#channels[@]};k++))
		do
			if [ ${channels[k]} = $1 ]; then
				tag="true"
				break
			fi
		done
	if [ "$tag" = "false" ] ; then
		echo "输入参数$1不合法"
		exit 1;
	fi
}

check(){
  if [ $? -ne 0 ];then
   echo "===================$1,执行失败======================="
   exit 1;
  fi
   echo "===================$1,执行成功========================"
}

buildFastaccessSdk(){
	cd $sdk_build_dir/fastaccess_sdk
	rm -rf local.properties
	sed -i "s/22.0.1/22.0.0/g" ./fastaccess_sdk/build.gradle
	gradle build
	check 'fastaccess_sdk'
}

if [ "" == "$1" ] ; then
    echo "在命令后面加一个渠道，如uc,如果打包全部,请输入all."
    exit 1;
else
    if [ "all" = "$1" ] ; then
	if [ "skip" = "$2" ];then
		echo "跳过SVN更新"
	else
		rm -rf $sdk_build_dir
		svn co $svn -r head $sdk_build_dir
	fi
		
#		buildFastaccessSdk
		
		for((i=0;i<${#channels[@]};i++))
			do
				sh $sdk_command_config_dir/deploy.sh ${channels[i]} $2
				check ${channels[i]}
			done
    else
		sdkCount=$#
		echo "=================== 参数合法性检查 ========================"
#		for (( i=1; i<=sdkCount; i++ ))
#			do
#				checkParas ${!i}
				checkParas $1
#			done
		echo "=================== SVN 检出开始 ========================"
#		rm -rf $sdk_build_dir/fastaccess_sdk
#		svn co $svn/fastaccess_sdk -r head $sdk_build_dir/fastaccess_sdk
#		for (( i=1; i<=sdkCount; i++ ))
#			do
				subStr=${!i}_fastaccess_sdk
				if [ ${!i} = "assets" ] || [ ${!i} = "manifest" ] || [ ${!i} = "smali" ] || [ ${!i} = "application" ] || [ ${!i} = "res" ] ;then
					subStr=${!i}
				fi
				rm -rf $sdk_build_dir/$subStr
				svn co $svn/$subStr -r head $sdk_build_dir/$subStr
				echo '取关联项目'
				while read -a ARRAY
					do
						aaa=(${ARRAY[@]})
						if [ $aaa ] && [ ${aaa[0]} = ${!i} ];then
							count=${#aaa[*]}
							let "count = $count - 1"
							echo '关联项目个数：'$count
							for((m=1;m<${#aaa[@]};m++))
								do
									echo "检出项目："${aaa[m]}
									rm -rf $sdk_build_dir/${aaa[m]}
									svn co $svn/${aaa[m]} -r head $sdk_build_dir/${aaa[m]}
								done
							break
						fi
					done < $sdk_command_config_dir/channel.txt
#			done
		echo "=================== 打包、部署 ========================"
		
#		buildFastaccessSdk
		
		for (( i=1; i<=sdkCount; i++ ))
			do
				sh $sdk_command_config_dir/deploy.sh ${!i} $2
				check ${!i}
			done
    fi
fi



