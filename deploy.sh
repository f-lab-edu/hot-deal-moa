#!/bin/bash

#=================================== 다른 서버 헬스 체크======================================

echo "> other server check start"
for retry_count in {1..10};
do
  response=$(sudo curl -s http://$OTHER_IP/actuator/health)
  up_count=$(echo $response | grep 'UP' | wc -l)
  echo "> $retry_count : $response  : $up_count"
  if [ $up_count -ge 1 ]; then
    echo "> other server health check success"
    break
  fi
  if [ $retry_count -eq 10 ]; then
    echo "> other server health check fail"
    exit 1
  fi
  echo "> retry 10 seconds after"
  sleep 10
done

#===================================프로세스 종료======================================

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

nohup java -jar -Djasypt.encryptor.password=$JASYPT_PASSWORD -Dspring.profiles.active=prod -Duser.timezone=Asia/Seoul hot-deal-moa.jar > /dev/null 2>&1 &
sleep 10

#=================================== 현재 서버 헬스 체크======================================

echo "> current server check start"
for retry_count in {1..10};
do
  response=$(sudo curl -s http://localhost/actuator/health)
  up_count=$(echo $response | grep 'UP' | wc -l)
  echo "> $retry_count : $response  : $up_count"
  if [ $up_count -ge 1 ]; then
    echo "> current server health check success"
    break
  fi
  if [ $retry_count -eq 10 ]; then
    echo "> current server health check fail"
    exit 1
  fi
  echo "> retry 10 seconds after"
  sleep 10
done

sleep 60