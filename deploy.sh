#!/bin/bash
echo "> now ing app pid find!"
CURRENT_PID=$(pgrep -f hot-deal-moa)
echo "$CURRENT_PID"
if [ -z $CURRENT_PID ]; then
  echo "> no ing app."
else
  echo "> kill -9 $CURRENT_PID"
  kill -9 $CURRENT_PID
  sleep 3
fi
echo "> new app deploy"

cd /hotdealmoa/build/libs/
JAR_NAME=$(ls | grep 'SNAPSHOT.jar')
echo "> JAR Name: $JAR_NAME"

nohup java -jar $JASYPT_PASSWORD -Duser.timezone=Asia/Seoul $JAR_NAME 1>/hotdealmoa/application.log 2>&1 &
sleep 2
