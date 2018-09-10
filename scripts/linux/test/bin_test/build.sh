#!/bin/sh

source $WORKSPACE_HOME/runtime/bin/env.sh
source $WORKSPACE_HOME/runtime/bin/util.sh
build_dir=/root/build
deploy_dir=/root/deploy




###1代表provider 2代表web

profile="test"
svn=http://svn.hoolai.com/access-platform
trunk_svn=$svn/trunk/fast_access
branches=$svn/branches/fast_access

if [ $# -eq 1 ];then
echo "主干版本部署开始"
elif [ $# -eq 3 ];then
   echo "分支版本部署开始"
   if [ "$2" != "load_test" ] && [ "$2" != "test" ];then
		echo "参数2必须是load_test或者test"
		exit 1;
   fi
else
   echo "参数输入错误，参数个数不对！"
   exit 1
fi

if [ "$2" = "load_test" ] && [ ! "$3" ];   then
   echo "load_test环境不能部署trunk"
   exit 1
fi

if [ "$2" = "load_test" ]; then
  profile="load_test"
fi

svn_url=$trunk_svn
svn_dir=$build_dir/$profile/trunk/fast_access

if [[ $profile = "load_test" ]] && [ $3 ];then
   svn_url=$branches/$3
   svn_dir=$build_dir/$profile/branches/fast_access/$3
fi

if [ -d $current_svn_dir ];then
  svn co $svn_url  $svn_dir
  check "检出SVN"
else
  svn update $svn_dir
  check "更新SVN"
fi


if [ $1 ] && [ $1 != "all"  ];then
  echo $1"_true"
fi

if [ $1 ] && [ $1 != "all" ];then
for (( i=0; i<${#apps[@]}; i++  ))
do
    attr=(${apps[$i]})
    if [ ${attr[0]} = $1 ];then
        echo "-------------------------------------------------------------"
        app=${attr[0]}
        type=${attr[1]}
        target_app_name="$app"-assembly.tar.gz
        provider_app=${app/_provider/}
        current_build_dir=$svn_dir//provider/$provider_app/$app
        current_deploy_dir=$deploy_dir/$profile
        target_app_name_path=$current_build_dir/target/$target_app_name
        if [ "$type" = "2" ];then
          current_build_dir=$svn_dir/open/$app
          target_app_name="$app".war
          current_svn_url=$svn_url/open/$app
          target_app_name_path=$current_build_dir/target/$target_app_name
        fi
        if [ "$type" = "3" ];then
          current_build_dir=$svn_dir/common/$app
          target_app_name="$app".zip
          target_app_name_path=$current_build_dir/target/$target_app_name
        fi       

        echo $current_build_dir
        mvn -f $current_build_dir/pom.xml -Dprofile=$profile clean deploy
        check "$app mvn 构建"
        cp -R  $target_app_name_path  $current_deploy_dir/
        check  复制"$target_app_name_path"到"$current_deploy_dir"目录
        if [ "$profile" = "load_test" ] && [ "$3"  ];then
          scp  $current_deploy_dir/$target_app_name root@60.28.203.113:/root/deploy/
          check "上传到正式环境$target_app_name" 
        fi
        mvn -f $current_build_dir/pom.xml -Dprofile=$profile clean
        
    fi
done
fi


if [ "$1" = "all" ];
then
  mvn -f $svn_dir/pom.xml -Dprofile=$profile clean deploy
  check "mvn构建all"
for (( i=0; i<${#apps[@]}; i++  ))
do
    attr=(${apps[$i]})
    app=${attr[0]}
        if [ ${attr[1]} = "1" ];then
           cp -R $svn_dir/provider/${app/_provider/}/$app/target/"$app"-assembly.tar.gz  $deploy_dir/$profile/
           check "复制$app到$deploy_dir/$profile目录"
        elif [ "${attr[1]}" = "2" ];then
           cp -R $svn_dir/open/$app/target/$app.war $deploy_dir/$profile/
           check "复制$app到$deploy_dir/$profile目录"
        else
           cp -R $svn_dir/common/$app/target/$app.zip $deploy_dir/$profile/
           check "复制$app到$deploy_dir/$profile目录"
        fi
done 
if [ "$profile" = "load_test" ];then
   scp   $deploy_dir/$profile/* root@60.28.203.113:/root/deploy/
   check "上传到所有包正式环境"
fi
 mvn -f $svn_dir/pom.xml -Dprofile=$profile clean
fi



if [ ! $1 ];then
  echo "输入错误,请输入以下值，全部请输入all"
  for (( i=0; i<${#apps[@]}; i++  ))
  do
    attr=(${apps[$i]})
    echo ${attr[0]}
  done
fi




