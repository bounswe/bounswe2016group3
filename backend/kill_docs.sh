#!/bin/bash
kill $(cat apidocs/server.pid)
rm apidocs/server.pid
