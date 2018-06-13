# Docker Development Container
## Why Does This Exist?

1. A consistent, isolated, build environment is important
2. Using this, you can develop in Clojure without the risks of having a
   JVM/JDK installed on your host system!
3. The use of alternative JVM hosts is made simpler
4. This container initializes and starts a headless REPL for development.

## Usage

1. Install and run [Docker](https://docs.docker.com/docker-for-mac/install/#install-and-run-docker-for-mac)
2. Create a =docker-compose= configuration:
```yaml
version: "3.5"
services:
  clojure:
    image: tobytripp/clj-builder:latest
    environment:
      APP_HOME: "${PWD}"
    build:
      context: .
      args:
        APP_HOME: "${PWD}"
    volumes:
      - ./src:${PWD}/src
      - ./test:${PWD}/test
      - ./log:${PWD}/log
    command: repl
    ports:
      - "5888:5888"
```
3. `docker-compose up -d clojure`
