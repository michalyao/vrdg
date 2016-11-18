# vrdg
---------------------------------------------
[![Build Status](https://travis-ci.org/michalyao/vrdg.svg?branch=master)](https://travis-ci.org/michalyao/vrdg)

### A simple yet powerful server for static files.

## Required
- Maven 3.3.x
- JDK 1.8+

## Build
``` shell
git clone https://github.com/michalyao/vrdg.git

cd vrdg

mvn package
```

## Usage

``` shell
java -jar vrdg-1.0-fat.jar vrdg -d -p 80 -h localhost - r .
```
**note:** In windows you should add -Dfile.encoding=UTF-8 to force the JVM instance use UTF-8 other than GBK.

## More
Docker(todo.)
