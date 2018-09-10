# !/bin/bash

pid=$(lsof -i:4000|grep hexo|tail -1|awk '"$1"!=""{print $2}')

if [ -z $pid ]
then
   echo "No process to be killed"
else
   kill -9 $pid
   echo "Process pid=$pid has been killed"
fi

cd /root/build/
git clone ssh://taojiaxuan@code.hoolai.com:24813/chenpeng1/hexo-admin.git

if [ -d "hexo_admin_backup" ]
then
    rm -rf hexo_admin_backup
fi

if [ -d "hexo-admin/" ]
then
    mv hexo_admin hexo_admin_backup
    mv hexo-admin hexo_admin
    cp hexo_admin_backup/db.json ./
fi

cd hexo_admin
cd node_modules/hexo-admin/
npm install
cd ../..
npm install
#touch db.json
#rm db.json
#cd ~/bin/docs/
#sh restart_docs.sh
nohup hexo server -d > docs.log &

