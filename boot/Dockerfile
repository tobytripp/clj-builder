# -*- mode: dockerfile -*-
ARG  JAVA_VERSION=8
FROM delitescere/jdk:${JAVA_VERSION}
LABEL maintainer="Toby Tripp <toby@tripp.net>"

RUN apk add --no-cache --virtual=.build-dependencies curl coreutils && \
    apk add --no-cache --virtual=.run-dependencies bash ca-certificates

ENV BIN_PATH /usr/local/bin
RUN mkdir -p $BIN_PATH

RUN mkdir -p $BOOT_INSTALL \
  && wget -q https://github.com/boot-clj/boot-bin/releases/download/2.7.2/boot.sh \
  && echo "Comparing installer checksum..." \
  && echo "f717ef381f2863a4cad47bf0dcc61e923b3d2afb *boot.sh" | sha1sum -c - \
  && mv boot.sh $BOOT_INSTALL/boot \
  && chmod 0755 $BOOT_INSTALL/boot

ARG APP_HOME=/usr/src/app
ENV PATH=$PATH:$BOOT_INSTALL
ENV BOOT_AS_ROOT=yes
ENV BOOT_CLOJURE_VERSION=1.9.0
ENV BOOT_LOCAL_REPO=/usr/local/lib/m2

RUN mkdir -p $APP_HOME
WORKDIR      $APP_HOME

VOLUME $APP_HOME/src           \
       $APP_HOME/test          \
       $APP_HOME/target        \
       $APP_HOME/resources     \
       $APP_HOME/log
EXPOSE 5888
EXPOSE 80
EXPOSE 8080

RUN boot

COPY docker-entrypoint.sh is_ready $BIN_PATH/

ENTRYPOINT ["docker-entrypoint.sh"]
CMD ["repl"]