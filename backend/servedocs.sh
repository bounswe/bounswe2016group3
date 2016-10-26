#!/bin/bash

mvn -q javadoc:javadoc
cd target/site/apidocs
python -m SimpleHTTPServer 5000 &> server.log &
echo $! > server.pid
