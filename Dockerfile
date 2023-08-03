# Build Stage
FROM maven:3.8.4-openjdk-17-slim as build
WORKDIR /build

COPY pom.xml .
COPY src src

# Install the specific JAR as a Maven dependency
COPY target/dependency/company-feature-toggles-sdk-1.0-SNAPSHOT.jar company-feature-toggles-sdk-1.0-SNAPSHOT.jar
RUN mvn install:install-file -Dfile=company-feature-toggles-sdk-1.0-SNAPSHOT.jar -DgroupId=com.company.feature-toggles -DartifactId=company-feature-toggles-sdk -Dversion=1.0-SNAPSHOT -Dpackaging=jar


COPY pom.xml .
RUN mvn dependency:go-offline
#RUN mvn package -DskipTests
RUN mvn install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# Final Stage
FROM openjdk:17-alpine
VOLUME /tmp

ARG DEPENDENCY=/build/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib ./lib
COPY --from=build ${DEPENDENCY}/META-INF ./META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes .

ENTRYPOINT ["java","-cp",".:lib/*","com.project.MoviesApplication"]

