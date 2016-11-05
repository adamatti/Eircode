FROM java:8

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

COPY gradle            /usr/src/app/gradle
COPY settings.gradle   /usr/src/app
COPY system.properties /usr/src/app
COPY gradlew           /usr/src/app

COPY build.gradle /usr/src/app
RUN ./gradlew idea #using "idea" to download test dependencies

COPY . /usr/src/app

EXPOSE 4567

#required for dokku/heroku
CMD ./gradlew run