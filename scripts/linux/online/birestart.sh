#!/bin/bash
#!/bin/sh
rsync -avzr --progress --delete --exclude '.svn': -e'ssh -p 22'  /data/workspace/common/bi_java_client/  root@$1:/data/workspace/common/bi_java_client/
ssh  root@$1 /data/workspace/common/bi_java_client/biservices.sh restart
