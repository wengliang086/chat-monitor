#!/bin/sh

JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_91.jdk/Contents/Home
echo $JAVA_HOME

basepath=$(cd `dirname $0`; pwd)
cd $basepath

export sdk_build_dir=$WORKSPACE_HOME/build/fast_access_sdk_dir
sdk_command_config_dir=conf
export sdk_deploy_dir=$WORKSPACE_HOME/deploy/sdk
svn=http://svn.hoolai.com/access-platform/trunk/fast_access_sdk


sh ../util_sdk/check_eclipse_studio.sh


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

sdkCount=$#

if [ "" == "$1" ] ; then
    echo "在命令后面加一个渠道，如uc,如果打包全部,请输入all."
    exit 1;
else
    if [ "all" = "$1" ] ; then
		i=0
		if [ "" != "$2" ] ; then
			if [ "index" = "$2" ] ; then
				if [ "" == "$3" ] ; then
					echo "缺少索引值参数"
				else
					i=$3
				fi
			else
				for((k1=0;k1<${#channels[@]};k1++))
					do
						if [ ${channels[k1]} = $2 ]; then
							i=$k1
							break
						fi
					done
			fi
			if [ $i == 0 ] ; then 
				echo "参数输入不合法，应该输入 all index 10（索引） 或者 all uc（开始渠道）"
				exit 1;
			fi
		fi
		
		echo "执行起始索引是：$i"
		
		if [ "skip_svn" != "${!sdkCount}" ] ; then
			rm -rf $sdk_build_dir
			svn co $svn -r head $sdk_build_dir
		else
			echo "跳过SVN更新"
		fi
		
		for((;i<${#channels[@]};i++))
			do
				echo "开始打包渠道：${channels[i]}"
				sh $sdk_command_config_dir/deploy.sh ${channels[i]} "dev"
				check ${channels[i]}
			done
    else
		echo "=================== 参数合法性检查 ========================"
		for (( i=1; i<=sdkCount; i++ ))
			do
				checkParas ${!i}
			done
		echo "=================== SVN 检出开始 ========================"
		rm -rf $sdk_build_dir/fastaccess_sdk
		svn co $svn/fastaccess_sdk -r head $sdk_build_dir/fastaccess_sdk
		for (( i=1; i<=sdkCount; i++ ))
			do
				subStr=${!i}_fastaccess_sdk
				if [ ${!i} = "assets" ] || [ ${!i} = "manifest" ] || [ ${!i} = "smali" ] || [ ${!i} = "application" ] || [ ${!i} = "res" ] ;then
					subStr=${!i}
				fi
				rm -rf $sdk_build_dir/$subStr
				svn co $svn/$subStr -r head $sdk_build_dir/$subStr
				
				ss1="false"
				ss2="false"
				if [ -d $sdk_build_dir/$subStr/libs/armeabi ]; then
					ss1="true"
				fi
				if [ -d $sdk_build_dir/$subStr/libs/armeabi-v7a ]; then
					ss2="true"
				fi
				echo $ss1
				echo $ss2
				if [ $ss1 = $ss2 ]; then
					echo "armeabi与armeabi-v7a检查正确"
				else
					echo "armeabi与armeabi-v7a必须同时存在"
					exit 1
				fi


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
			done
		echo "=================== 打包、部署 ========================"
		for (( i=1; i<=sdkCount; i++ ))
			do
				sh $sdk_command_config_dir/deploy.sh ${!i} "dev"
				check ${!i}
			done
    fi
fi



