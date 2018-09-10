#!bin/sh

local_ios_shell_dir=/root/workspace/common/ios_package_tools
source $WORKSPACE_HOME/runtime/bin/env_ios.sh

echo "==========开始上传IOS_SHELL=========="
##scp -P 36000 -r $local_ios_shell_dir  fastsdk@60.28.203.117:$romote_ios_shell_dir
rsync -avzr --progress -e'ssh -p 22' $local_ios_shell_dir/*  $DEPLOY_USER@$DEPLOY_SERVER_IP:$IOS_SHELL_DIR
echo "==========上传完成=========="
