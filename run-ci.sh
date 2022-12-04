#!/bin/bash

unset http_proxy
unset https_proxy
unset HTTP_PROXY
unset HTTPS_PROXY
export no_proxy="<nome_aplicacao_back>, wiremock, localstack"
export NO_PROXY="<nome_aplicacao_back>, wiremock, localstack"

APP_HEALTHCHECK="http://${APP_HOST}:8080/actuator/health"

count=0
code=0

while [$count -lt 60]
do
  code=`curl -sL -w "%{http_code}\\n" ${APP_HEALTHCHECK} -o /dev/null`
  echo "HTTP_STATUS=${code}"
  if [ "x$code" = "x200"]
  then
      echo "Starting integration tests..."
      mvn verify
      break
  fi

  echo "waiting application started..."
  sleep 10
  ((count=count+1))
  echo "$count"

done

if [ "x$code" != "x200" ]
then
    echo "Application do not started..."
    echo "APP=${APP_HEALTHCHECK}"
    echo "HTTP_STATUS=${code}"
    exit 1
fi