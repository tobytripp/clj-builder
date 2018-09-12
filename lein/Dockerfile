# -*- mode: dockerfile -*-
ARG  JAVA_VERSION=8
FROM delitescere/jdk:${JAVA_VERSION}
LABEL maintainer="Toby Tripp <toby@tripp.net>"

ENV CLOJURE_VERSION=1.9.0.391

RUN echo 'http://dl-cdn.alpinelinux.org/alpine/edge/main' >> \
      /etc/apk/repositories
RUN echo '@testing http://dl-cdn.alpinelinux.org/alpine/edge/testing' >> \
      /etc/apk/repositories

RUN apk add -U --upgrade apk-tools
RUN apk add --no-cache --virtual=.build-dependencies coreutils && \
    apk add --no-cache bash curl ca-certificates rlwrap@testing

ENV BIN_PATH /usr/local/bin
RUN mkdir -p $BIN_PATH

WORKDIR /tmp

RUN curl -O https://download.clojure.org/install/linux-install-$CLOJURE_VERSION.sh \
 && chmod +x linux-install-$CLOJURE_VERSION.sh \
 && ./linux-install-$CLOJURE_VERSION.sh

ENV LEIN_ROOT true
ENV LEIN_DOWNLOAD_SHA f60e5faa00f97ed756af0433856945c1cbe4c53d3bde9fcbcd1d0df8166c0b386e7b28165af8e7d08ba93a78c8f936bd04068d25cc23d9fb9700d25b79d9d6eb
ENV LEIN_URI https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein

RUN set -eux -o pipefail                          && \
    cd $BIN_PATH                                  && \
    curl -fLsS $LEIN_URI > lein                   && \
    echo $LEIN_DOWNLOAD_SHA lein | sha512sum -c - && \
    chmod a+x lein                                && \
    apk del .build-dependencies                   && \
    rm -f APKINDEX.*                              && \
    rm -rf /tmp/* /var/cache/apk/*

ENV M2_REPO=/usr/local/lib/m2
RUN mkdir -p $M2_REPO /etc/leiningen/ && \
    echo "{:system { :local-repo \"${M2_REPO}\" }}" > /etc/leiningen/profiles.clj

ARG APP_HOME=/usr/src/app
VOLUME $APP_HOME/src           \
       $APP_HOME/test          \
       $APP_HOME/target        \
       $APP_HOME/resources     \
       $APP_HOME/log           \
       $HOME/.lein/profiles.d
EXPOSE 5888
EXPOSE 80
EXPOSE 8080

RUN mkdir -p     $APP_HOME
WORKDIR          $APP_HOME
COPY project.clj $APP_HOME/
RUN echo 'exit' | \
  lein \
    update-in :repl-options assoc :init-ns clojure.core -- \
    repl
RUN echo $APP_HOME

COPY docker-entrypoint.sh is_ready $BIN_PATH/

ENTRYPOINT ["docker-entrypoint.sh"]
CMD ["uberjar"]
