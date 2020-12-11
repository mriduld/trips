FROM openjdk:8-jdk-stretch

RUN apt update && apt -y install \
	wget \
	openjdk-8-jdk \
	maven \
	vim less

WORKDIR /app

CMD mvn clean package install && mvn -pl rest-service -Dspring-boot.run.jvmArguments="-Dspring.config.location=file:///app/config/application.properties -Dlogging.config=file:///app/config/logback.xml" spring-boot:run