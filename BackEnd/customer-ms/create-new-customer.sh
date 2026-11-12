#!/bin/bash

set -e


echo 'Limpando e construindo o projeto de customer'
./gradlew clean build -x test

echo 'Gerando a imagem do customer-ms'
docker build -t boinha/customer-ms:latest .