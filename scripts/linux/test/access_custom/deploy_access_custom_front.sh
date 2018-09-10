# !/bin/bash
 
 
 check(){
     if [ $? -ne 0 ];then
         echo "===================$1,执行失败======================="
         exit 1;
     fi
     echo "===================$1,执行成功========================"
 }
 
 RUNTIME_DIR="/root/workspace/runtime/app/access_custom_front"

profile="test"
if [ ! "$1" == "" ];then 
profile=$1
fi 
 svn_url="http://svn.hoolai.com/access-platform/trunk/hoolai_game_custom/customer_service_system"
 
 echo "access_custom_front 版本开始部署"
 
 if [ ! -d $RUNTIME_DIR ];then
   svn co $svn_url $RUNTIME_DIR
   check "检出SVN"
 else
   svn update $RUNTIME_DIR
   check "更新SVN"
 fi
 
 cd $RUNTIME_DIR
 
 if [ ! -d $RUNTIME_DIR/node_modules ];then
   cnpm install;
 fi
 
# nohup npm run $profile >/root/workspace/runtime/app/access_custom_front/null 2>&1 &
  npm run $profile 
 
 if [ "$profile" = "build" ];then
  echo "开始往线上copy"
  rsync -avzh --exclude '.svn' $RUNTIME_DIR/public/* fastsdk@119.29.21.153:/home/fastsdk/deploy/access_custom_front/
 fi
 
