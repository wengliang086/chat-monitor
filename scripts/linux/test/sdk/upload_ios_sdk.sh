#!bin/sh

source $WORKSPACE_HOME/runtime/bin/util.sh
source $WORKSPACE_HOME/runtime/bin/env_ios.sh

sdk_deploy_dir=/root/access/fastaccess_web/download/demo_online

if [ $1 ] && [ "$1" == "all" ];then

 rsync -avzr --progress --exclude '.svn' -e'ssh -p 22' $sdk_deploy_dir/"HoolaiSDKDemo.zip" $DEPLOY_USER@$DEPLOY_SERVER_IP:$HOOLAI_SDK_DEMO_DIR
# scp -P36000 $sdk_deploy_dir/"HoolaiSDKDemo.zip" fastsdk@60.28.203.117:/home/fastsdk/deploy/
 rsync -avzr --progress --exclude '.svn' -e'ssh -p 22' $sdk_deploy_dir/"AccessSDK_iOS.zip" $DEPLOY_USER@$DEPLOY_SERVER_IP:$HOOLAI_SDK_DEMO_DIR
# scp -P36000 $sdk_deploy_dir/"AccessSDK_iOS.zip" fastsdk@60.28.203.117:/home/fastsdk/deploy/
else 
# scp -P36000 $sdk_deploy_dir/"$1.zip" fastsdk@60.28.203.117:/home/fastsdk/deploy/
 rsync -avzr --progress --exclude '.svn' -e'ssh -p 22' $sdk_deploy_dir/"$1.zip" $DEPLOY_USER@$DEPLOY_SERVER_IP:$HOOLAI_SDK_DEMO_DIR
fi
 check "上传$1到包正式环境"

