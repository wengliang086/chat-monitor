#! /bin/bash
cd /root/build/
tar -zcvf hexo_admin.tar.gz hexo_admin/
rsync -avzr --progress --delete --exclude '.svn': -e'ssh -p 22'  hexo_admin.tar.gz  fastsdk@119.29.74.61:/home/fastsdk/doc/
