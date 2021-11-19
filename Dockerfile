FROM registry.access.redhat.com/ubi8/openjdk-11:1.3-17
ARG VERSION=0.0.1
COPY target/flyway-service-${VERSION}-SNAPSHOT.jar app.jar
EXPOSE 8080
USER 185
ENTRYPOINT exec java -jar ./app.jar