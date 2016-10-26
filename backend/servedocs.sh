#!/bin/bash

cd apidocs
python -m SimpleHTTPServer 5000 &> server.log &
echo $! > server.pid
