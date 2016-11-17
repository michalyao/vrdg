FROM java:8u77-jre-alpine

MAINTAINER yaoyao<yaoyao0777@gmail.com>

ENV VERTICLE_HOME /opt/verticles

ENV VERTICLE_FILE vrdg-1.0-fat.jar

ENV ROOTDIR /webroot

RUN mkdir -p $ROOTDIR

VOLUME [$ROOTDIR]

EXPOSE 80

COPY target/$VERTICLE_FILE  $VERTICLE_HOME

WORKDIR $VERTICLE_HOME

ENTRYPOINT ["sh", "-c"]

# docker 版默认开放80端口，使用 -p 参数在启动容器时绑定宿主端口
CMD ["java -jar $VERTICLE_FILE -p 80"]
