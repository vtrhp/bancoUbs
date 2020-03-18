#!/bin/bash

codigo_http=$(curl --write-out %{http_code} --silent --output /dev/null http://localhost:8080/actuator/health)

echo $codigo_http

if [ $codigo_http -ne 200 ]; then

    mvn spring-boot:run

fi