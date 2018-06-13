#!/bin/bash

export GATEWAY=`netstat -nr | awk '/^0\.0\.0\.0/ { print $2 }'`
export BOOT="boot"

case "$1" in
    'bash')    exec bash ;;
    'repl')    exec $BOOT repl -s -p 5888 -b 0.0.0.0 wait ;;
    *)         exec $BOOT $@ ;;
esac
