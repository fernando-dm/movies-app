#FROM eclipse-temurin:17
#ENV TZ=America/Argentina/Buenos_Aires
#RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
#
#COPY target/*.jar app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]

FROM maven:3.8.4-openjdk-17-slim as build
WORKDIR /build

COPY pom.xml .
RUN mvn dependency:go-offline
COPY src src

RUN mvn install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:17-alpine
VOLUME /tmp
ARG DEPENDENCY=/build/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.project.MoviesApplication"]

