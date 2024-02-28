FROM openjdk:8
#FROM openjdk:17-jdk-slim

ADD target/javashop.shop-latest.jar ./app.jar
EXPOSE 8010

ADD 'https://dtdg.co/latest-java-tracer' dd-java-agent.jar

ENV DD_SERVICE shop
ENV DD_VERSION 1.4

LABEL com.datadoghq.tags.service="shop"
LABEL com.datadoghq.tags.version="1.4"

ARG DD_GIT_REPOSITORY_URL
ARG DD_GIT_COMMIT_SHA
ENV DD_GIT_REPOSITORY_URL=${DD_GIT_REPOSITORY_URL} 
ENV DD_GIT_COMMIT_SHA=${DD_GIT_COMMIT_SHA}

#ADD https://github.com/signalfx/splunk-otel-java/releases/latest/download/splunk-otel-javaagent-all.jar .

ENTRYPOINT java -javaagent:dd-java-agent.jar \
  -Ddd.profiling.enabled=true \
  -XX:FlightRecorderOptions=stackdepth=256 \
  -Ddd.agent.host=datadog  \
  #-Ddd.agent.host=192.168.1.32 \
  -Ddd.logs.injection=true \
  -Ddd.dynamic.instrumentation.enabled=true \
  -Ddd.trace.sample.rate=1 \
  -Ddd.appsec.enabled=true \
  -Ddd.iast.enabled=true  \
  -Ddd.data.streams.enabled=true \
  -Ddd.service=shop \ 	
  -Ddd.env=${USERNAME}_xApm_Instrumentation_Shop -jar app.jar

# Test Start: ENTRYPOINT java  -Dotel.resource.attributes=service.name=shop,deployment.environment=${USERNAME}2_Apm_Instrumentation_Shop -jar app.jar

