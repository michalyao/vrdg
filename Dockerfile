FROM java:8u77-jre-alpine

MAINTAINER yaoyao<yaoyao0777@gmail.com>

ENV VERTICLE_HOME /opt/verticles

ENV VERTICLE_FILE vrdg-fat.jar

ENV ROOTDIR /webroot

RUN mkdir -p $ROOTDIR

VOLUME [$ROOTDIR]

EXPOSE 80

COPY target/$VERTICLE_FILE  $VERTICLE_HOME

WORKDIR $VERTICLE_HOME

ENTRYPOINT ["sh", "-c"]

CMD ["java -jar $VERTICLE_FILE"]
