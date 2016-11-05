ci: clean build
	docker-compose -f docker-compose-ci.yml run tests ./gradlew e2eTest --stacktrace
	${MAKE} clean

run: clean build
	docker-compose up
	${MAKE} clean

build:
	docker-compose -f docker-compose.yml -f docker-compose-ci.yml build

clean:
	docker-compose -f docker-compose.yml -f docker-compose-ci.yml kill
	docker-compose -f docker-compose.yml -f docker-compose-ci.yml rm -fv
