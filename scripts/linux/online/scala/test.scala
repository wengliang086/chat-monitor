#!/bin/sh 
WORKSPACE=/data/bin/scala
source $WORKSPACE/scala_env.sh
LIB_DIR=$WORKSPACE/libs
LIB_JARS=`ls $LIB_DIR|grep .jar|awk '{print "'$LIB_DIR'/"$0}'|tr "\n" ":"`
ips=`for (( m=0; m<${#ips[@]}; m++  ));do echo ${ips[$m]};done |tr "\n" ","`

exec env ips="$ips"  /data/bin/scala/scala-2.11.8/bin/scala  -classpath  /data/bin/scala/conf:$LIB_JARS   "$0" "$@"
!#

import sys.process._ 
import com.hoolai.fastaccess.common.hutil.tencentcloud.QCloudApi
import com.hoolai.fastaccess.common.hutil.tencentcloud.QCloudRollingUpdate
import com.hoolai.fastaccess.common.hutil.tencentcloud.vo.QCloudInstance
import com.hoolai.fastaccess.common.hutil.tencentcloud.vo.QCvm
import com.hoolai.fastaccess.common.hutil.tencentcloud.Deployment
import com.hoolai.fastaccess.common.hutil.tencentcloud.checker.QCheckList
import com.hoolai.fastaccess.common.hutil.tencentcloud.checker.Checkers
import scala.collection.JavaConverters._






val secretid  = "AKIDvjNVWOse0DF6mGGj6QdhZGrGUWs6K4mI"
val secretkey = "UsGTVM2XpwpFYgfTCj6JKEzYvCJFzXt5"
val region    = "gz"
val lbid      = "lb-04gybwnc"



def getCvmList:java.util.List[QCvm] = {

var ips:String = sys.env("ips")
var ipsArray:Array[String] = ips.split(",")
var lanIps = ipsArray.map(s => s.split(" ")(0) )

val api:QCloudApi = new QCloudApi(secretid,secretkey,region);

var cvmlist = api.getCVM(lanIps.toList.asJava);

cvmlist.asScala.foreach( cvm => ipsArray.foreach( s => if( s.split(" ")(0)== cvm.getLanIp){  cvm.setGroup(Integer.valueOf( s.split(" ")(1) ) ) }) )

cvmlist
}


getCvmList.asScala.foreach( cvm => println(cvm.getLanIp))













