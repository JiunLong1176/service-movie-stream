#!/bin/bash

init() {
    #set env variables
    export VERSION="0.0.1-SNAPSHOT"
    export BUILD_DATE=`date`

    if [ "$ENVIRONMENT" == "local" ]; then
        export BUILD_NUMBER="1"
        export SPRING_DATASOURCE_URL="mysql:jdbc:mysql://localhost:3306/sys?useLegacyDatetimeCode=false"
        export SPRING_DATASOURCE_USERNAME="root"
        export SPRING_DATASOURCE_PASSWORD="root123@"
    fi
}

compile() {
    #compile the sprint-boot service
    rm -rf build
    gradle clean build
}

args() {
    if [ "$1" == "" ] || [ "$2" == "" ]; then
        echo "\nusage: build service environment"
        exit -1
    fi
    export SERVICE="$1"
    export ENVIRONMENT="$2"
    export ARG_DOCKER="$3"
}

run() {
    args "$@"
    init || exit 1

    # If ARG_DOCKER is set to "no-docker", don't dockerize, otherwise do
    if [[ "$ARG_DOCKER" == "no-docker" ]]; then
        # Run the built jar file directly
        ./gradlew clean build
        java -Dspring.profiles.active="$ENVIRONMENT" -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=9999 -jar ./build/libs/service-"$SERVICE"-$VERSION.jar
    else
        # Dockerize the application
        dockerize || exit 1
    fi
}

run $@
