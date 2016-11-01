(forked from https://bitbucket.org/ntakimura/android-nginx-app)

Android Application with Nginx
====================

Android Application with Nginx is the demo application for [android-nginx].
You can try using [nginx] server on your android device.

Nginx Base Version
====================

Nginx release 1.7.0

Application Build Manual
====================

Install build tools
--------------------
Install following build tools.

* Apache Ant - http://ant.apache.org/
* Android SDK - http://developer.android.com/sdk/index.html
* Android NDK - http://developer.android.com/tools/sdk/ndk/index.html

| name | value |
| :--- | :---- |
| HOME | your home directory |
| ANDROID_HOME | Android SDK install path |
| ANDROID_NDK_HOME | Android NDK install path |
| ANT_HOME | Apache Ant install path |

And, make standalone toolchain.

```
#!sh
% $ANDROID_NDK_HOME/build/tools/make-standalone-toolchain.sh \
    --platform=android-19 --install-dir=$HOME/local/android-toolchain
```


Configuration nginx core and modules
--------------------
Connect android device with USB cable, or launch android emulator.

Run command to configure nginx core and libraries.

```
#!sh
% cd jni/nginx

% auto/configure \
    --crossbuild=android-arm \
    --prefix=/sdcard/nginx \
    --with-cc=$HOME/local/android-toolchain/arm-linux-androideabi/bin/gcc \
    --without-pcre --without-http_rewrite_module --without-http_userid_module \
    --with-cc-opt=-Wno-sign-compare
```

Build library and application
--------------------
Build android application ```ant```.

```
#!sh
% $ANT_HOME/bin/ant debug
```


Application Usage
====================

Use application
--------------------
Nginx needs config files and logs directory to run.
Please copy `sdcard/nginx` to `/sdcard/nginx` in the android device.

```
/sdcard
    +-- nginx/
          +-- conf/
          |     +-- nginx.conf
          |     +-- mime.types
          |
          +-- html/
          |     +-- index.html
          |     +-- 50x.html
          |
          +-- logs/
```

After that, install android-nginx-app.apk,
and open http://localhost:8080/ with web browser.

Sample code
--------------------

```java
// create Nginx instance
Nginx nginx = Nginx.create();

// start nginx
nginx.start();
```

Class org.bitbucket.ntakimura.nginx.Nginx has similar methods
to com.sun.net.httpserver.HttpServer.
Nginx starts and stops by Nginx#start and Nginx#stop.
Android System Library does not include com.sun.net.httpserver.HttpServer,
so class Nginx does not extend that class.

Class Nginx is designed as singleton model.
Nginx#create returns same instance, when it is called any times.



License
====================

The application released under [WTFPL]

                DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
                        Version 2, December 2004

     Copyright (C) 2014 Naoki Takimura <n.takimura@gmail.com>

     Everyone is permitted to copy and distribute verbatim or modified
     copies of this license document, and changing it is allowed as long
     as the name is changed.

                DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
       TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION

      0. You just DO WHAT THE FUCK YOU WANT TO.

Nginx's license is not [WTFPL].
Check its license in http://nginx.org/LICENSE .


[developers.android.com]: http://developer.android.com/tools/sdk/ndk/index.html
[nginx]: http://nginx.com/
[android-nginx]: https://bitbucket.org/ntakimura/android-nginx
[WTFPL]: http://www.wtfpl.net/
[nginx-license]: http://nginx.org/LICENSE
