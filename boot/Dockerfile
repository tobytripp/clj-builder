# -*- mode: dockerfile -*-
ARG  JAVA_VERSION=8
FROM delitescere/jdk:${JAVA_VERSION}
LABEL maintainer="Toby Tripp <toby@tripp.net>"

RUN apk add --no-cache --virtual=.build-dependencies curl coreutils && \
    apk add --no-cache --virtual=.run-dependencies bash ca-certificates

ENV BOOT_VERSION=2.7.2
ENV BOOT_INSTALL=/usr/local/bin/
ENV BOOT_HOME=/usr/local/share/boot
ENV BIN_PATH=/usr/local/bin

RUN mkdir -p $BOOT_INSTALL $BIN_PATH $BOOT_HOME \
  && curl -fLsS https://github.com/boot-clj/boot-bin/releases/download/2.7.2/boot.sh > boot.sh \
  && echo "Comparing installer checksum..." \
  && echo "f717ef381f2863a4cad47bf0dcc61e923b3d2afb *boot.sh" | sha1sum -c - \
  && mv boot.sh $BOOT_INSTALL/boot \
  && chmod 0755 $BOOT_INSTALL/boot \
  && apk del .build-dependencies

ENV PATH=$PATH:$BOOT_INSTALL:$BIN_PATH
ENV BOOT_AS_ROOT=yes
ENV BOOT_CLOJURE_VERSION=1.9.0
ENV BOOT_LOCAL_REPO=/usr/local/lib/m2

COPY profile.boot $BOOT_HOME/
COPY docker-entrypoint.sh is_ready $BIN_PATH/

ONBUILD ARG APP_HOME=/usr/src/app
ONBUILD ENV APP_HOME $APP_HOME
ONBUILD RUN mkdir -p $APP_HOME
ONBUILD WORKDIR $APP_HOME

EXPOSE 5888
EXPOSE 80
EXPOSE 8080

RUN boot

ENTRYPOINT ["docker-entrypoint.sh"]
CMD ["repl"]
