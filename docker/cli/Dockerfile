FROM openjdk:8-jdk-stretch

RUN apt update && apt -y install \
	wget \
	openjdk-8-jdk \
	maven \
	curl \
	jq \
	vim less

WORKDIR /app

CMD mvn clean package install