FROM gradle:7.6.1-jdk17
RUN mkdir /tmp/layer
ADD . /tmp/layer
WORKDIR /tmp/layer
RUN gradle clean build
RUN rm -r /tmp/layer/build/libs/*plain*.jar
RUN ls -lsah /tmp/layer/build/libs/
RUN mv /tmp/layer/build/libs/*.jar /tmp/app.jar

FROM openjdk:17-slim
COPY --from=0 /tmp/app.jar /tmp
ENV TZ America/Sao_Paulo
RUN ls /tmp
ENTRYPOINT ["java", "-Xms256m", "-Xmx512m","-jar", "/tmp/app.jar"]
EXPOSE 8080
