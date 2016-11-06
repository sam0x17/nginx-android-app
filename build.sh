#!/bin/bash
cd jni/nginx
./build_arm.sh || exit 1
cd ../../ || exit 1
adb install bin/android-nginx-debug.apk || exit 1
adb push sdcard/ / || exit 1
echo "DONE"
