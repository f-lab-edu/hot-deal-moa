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

cd /hotdealmoa/

nohup java -jar -Djasypt.encryptor.password=$JASYPT_PASSWORD -Dspring.profiles.active=prod -Duser.timezone=Asia/Seoul hot-deal-moa.jar  1>/hotdealmoa/application.log 2>&1 &
sleep 2
