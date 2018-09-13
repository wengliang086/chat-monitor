#!/bin/bash
cd `dirname $0`
./stop.sh $1
./start.sh
