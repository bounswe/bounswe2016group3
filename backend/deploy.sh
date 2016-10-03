#!/bin/bash

HOST=ec2-52-57-126-127.eu-central-1.compute.amazonaws.com
USER=ubuntu
KEYFILE=~/Downloads/group3.pem

mvn package
scp -i $KEYFILE target/backend-1.0-SNAPSHOT.jar $USER@$HOST:~/backend.jar
scp -i $KEYFILE conf.yml $USER@$HOST:~/conf.yml
ssh -i $KEYFILE $USER@$HOST ./restart.sh
