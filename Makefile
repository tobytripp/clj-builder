.PHONY: all clean install clobber test

test:
	APP_HOME=$PWD docker-compose build && \
	docker-compose up -d clojure && ./is_ready && \
	docker-compose stop && \
	docker-compose run clojure test builder.core-test

clean:
	docker-compose rm -fsv

clobber: clean
	docker rmi -f clj-builder
	docker system prune -f
