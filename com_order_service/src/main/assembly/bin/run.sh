JAVA_HOME="/usr/java/jdk1.7.0_60"
#check JAVA_HOME & java
noJavaHome=false
if [ -z "$JAVA_HOME" ] ; then
    noJavaHome=true
fi
if [ ! -e "$JAVA_HOME/bin/java" ] ; then
    noJavaHome=true
fi
if $noJavaHome ; then
    echo
    echo "Error: JAVA_HOME environment variable is not set."
    echo
    exit 1
fi


RUNNING_USER=root
APP_HOME=/usr/local/com.jc.order.service-0.0.1
APP_MAINCLASS=service.provider.ServerStartUp
CLASSPATH=$APP_HOME/lib/
for i in "$APP_HOME"/lib/*.jar;
do
   CLASSPATH="$CLASSPATH":"$i"
done

CLASSPATH=.:$CLASSPATH
echo ${CLASSPATH}
#JAVA_OPTS="-ms512m -mx512m -Xmn256m -Djava.awt.headless=true -XX:MaxPermSize=128m"
#==============================================================================
#set JAVA_OPTS
JAVA_OPTS="-server -Xms1G -Xmx1G -XX:MaxPermSize=64M  -XX:+AggressiveOpts -XX:MaxDirectMemorySize=1G"
#JAVA_OPTS="-server -Xms4G -Xmx4G -XX:MaxPermSize=64M  -XX:+AggressiveOpts -XX:MaxDirectMemorySize=6G"
#performance Options
#JAVA_OPTS="$JAVA_OPTS -Xss256k"
#JAVA_OPTS="$JAVA_OPTS -XX:+AggressiveOpts"
#JAVA_OPTS="$JAVA_OPTS -XX:+UseBiasedLocking"
#JAVA_OPTS="$JAVA_OPTS -XX:+UseFastAccessorMethods"
#JAVA_OPTS="$JAVA_OPTS -XX:+DisableExplicitGC"
#JAVA_OPTS="$JAVA_OPTS -XX:+UseParNewGC"
#JAVA_OPTS="$JAVA_OPTS -XX:+UseConcMarkSweepGC"
#JAVA_OPTS="$JAVA_OPTS -XX:+CMSParallelRemarkEnabled"
#JAVA_OPTS="$JAVA_OPTS -XX:+UseCMSCompactAtFullCollection"
#JAVA_OPTS="$JAVA_OPTS -XX:+UseCMSInitiatingOccupancyOnly"
#JAVA_OPTS="$JAVA_OPTS -XX:CMSInitiatingOccupancyFraction=75"
#JAVA_OPTS="$JAVA_OPTS -XX:CMSInitiatingOccupancyFraction=75"
#GC Log Options
#JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCApplicationStoppedTime"
#JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCTimeStamps"
#JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCDetails"
#debug Options
#JAVA_OPTS="$JAVA_OPTS -Xdebug -Xrunjdwp:transport=dt_socket,address=8065,server=y,suspend=n"
#==============================================================================

psid=0
CURR_DIR=`pwd`
cd `dirname "$0"`/..
COMSERVICE_HOME=`pwd`
cd $CURR_DIR

checkpid() {
   javaps=`$JAVA_HOME/bin/jps -l | grep $APP_MAINCLASS`
   if [ -n "$javaps" ]; then
      psid=`echo $javaps | awk '{print $1}'`
   else
   {
      psid=0
    }
   fi
}
start() {
   checkpid
 
   if [ $psid -ne 0 ]; then
      echo "================================"
      echo "warn: $APP_MAINCLASS already started! (pid=$psid)"
      echo "================================"
   else
       echo -n "Starting $APP_MAINCLASS.."
       echo "";
	    RUN_CMD="\"$JAVA_HOME/bin/java\""
		RUN_CMD="$RUN_CMD -DCOMSERVICE_HOME=\"$COMSERVICE_HOME\""
		RUN_CMD="$RUN_CMD -classpath \"$CLASSPATH\""
		RUN_CMD="$RUN_CMD $JAVA_OPTS"
		RUN_CMD="$RUN_CMD $APP_MAINCLASS  $@"
		RUN_CMD="$RUN_CMD >> \"$COMSERVICE_HOME/logs/console.log\" 2>&1 &"
		#echo $RUN_CMD
		eval $RUN_CMD

      #JAVA_CMD="nohup $JAVA_HOME/bin/java $JAVA_OPTS -classpath $CLASSPATH $APP_MAINCLASS >/dev/null 2>&1 &" 
       #JAVA_CMD="java -jar  &"
       #echo $JAVA_CMD
      #su - $RUNNING_USER -c "$JAVA_CMD"
      checkpid
	  echo $psid
      if [ $psid -ne 0 ]; then
         echo "(pid=$psid) [OK]"
      else
         echo "[Failed]"
      fi
   fi
}
stop() {
   checkpid
 
   if [ $psid -ne 0 ]; then
      echo -n "Stopping $APP_MAINCLASS ...(pid=$psid) "
      su - $RUNNING_USER -c "kill -9 $psid"
      if [ $? -eq 0 ]; then
         echo "[OK]"
      else
         echo "[Init Failed]"
      fi
 
      checkpid
      if [ $psid -ne 0 ]; then
         stop
      fi
   else
      echo "================================"
      echo "warn: $APP_MAINCLASS is not running"
      echo "================================"
   fi
}
status() {
   checkpid
 
   if [ $psid -ne 0 ];  then
      echo "$APP_MAINCLASS is running! (pid=$psid)"
   else
      echo "$APP_MAINCLASS is not running"
   fi
}
info() {
   echo "System Information:"
   echo "****************************"
   echo `head -n 1 /etc/issue`
   echo `uname -a`
   echo
   echo "JAVA_HOME=$JAVA_HOME"
   echo `$JAVA_HOME/bin/java -version`
   echo
   echo "APP_HOME=$APP_HOME"
   echo "APP_MAINCLASS=$APP_MAINCLASS"
   echo "****************************"
}
case "$1" in
start|begin)
start
;;
stop|shutdown)
stop
;;
restart)
stop
start
;;
status)
status
;;
info)
info
;;
*)
echo "ok,just done"
;;
esac
