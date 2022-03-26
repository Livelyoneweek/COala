#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

REPOSITORY=/home/ubuntu/app/step3
PROJECT_NAME=springboot-webservice

RESPONSE_CODE2=$(curl -s -o /dev/null -w "%{http_code}" https://ab.choideveloper.shop/profile)

CURRENT_PROFILE2=$(curl -s https://ab.choideveloper.shop/profile)
echo "> 현재 코드는 : $(RESPONSE_CODE2)"
echo "> 현재 real은 : $(CURRENT_PROFILE2)"

if [ ${CURRENT_PROFILE2} == real1 ]
    then
      IDLE_PROFILE=real2
    else
      IDLE_PROFILE=real1
    fi

    echo "최종 real은 : ${IDLE_PROFILE}"


echo "> Build 파일 복사"
echo "> cp $REPOSITORY/zip/*.jar $REPOSITORY/"

cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> 새 어플리케이션 배포"
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

IDLE_PROFILE=$(find_idle_profile)

echo "> $JAR_NAME 를 profile=$IDLE_PROFILE 로 실행합니다."

#nohup java -jar -Dspring.profiles.active=$IDLE_PROFILE week03-0.0.1-SNAPSHOT.jar
# classpath:/application.properties 는 프로젝트 내부 안에 프로퍼티스가 없으므로 제외해야함
nohup java -jar \
    -Dspring.config.location=classpath:/application-$IDLE_PROFILE.properties \
    -Dspring.profiles.active=$IDLE_PROFILE \
    $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &

#    -Dspring.config.location=classpath:/application.properties,classpath:/application-$IDLE_PROFILE.properties \
#nohup java -jar -Dspring.profiles.active=$IDLE_PROFILE -Dspring.config.location=file:///home/ubuntu/app/step3/application.properties,file:///home/ubuntu/app/step3/real-application.properties