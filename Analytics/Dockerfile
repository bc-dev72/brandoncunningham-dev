FROM gradle:jdk8-jammy AS BUILD
WORKDIR /usr/app/
COPY . .
RUN gradle build

# Package stage
FROM openjdk:8-jdk-alpine
ENV JAR_NAME=analytics.jar
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY --from=BUILD $APP_HOME .
EXPOSE 25565
ENTRYPOINT exec java -jar $APP_HOME/build/libs/$JAR_NAME
