#FROM openjdk:11 as build
#WORKDIR .
#
#COPY mvnw .
#COPY .mvn .mvn
#COPY pom.xml .
#COPY src src
#
#RUN ./mvnw install -DskipTests
#RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:11
VOLUME /tmp
ARG JAR_FILE=/core-runtime/target/*.jar
COPY run.sh .
COPY ${JAR_FILE} reqqa.jar
ENTRYPOINT ["./run.sh"]