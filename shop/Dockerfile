FROM openjdk:15
#FROM openjdk:17-jdk-slim

ADD target/javashop.shop-latest.jar ./app.jar
EXPOSE 8010

#EXPOSE 8001

COPY dd-java-agent.jar  .

ENV DD_SERVICE shop
ENV DD_VERSION 1.1

LABEL com.datadoghq.tags.service="shop"
LABEL com.datadoghq.tags.version="1.1"


#ADD https://github.com/signalfx/splunk-otel-java/releases/latest/download/splunk-otel-javaagent-all.jar .

ENTRYPOINT java -javaagent:dd-java-agent.jar \
  -Ddd.profiling.enabled=true \
  -XX:FlightRecorderOptions=stackdepth=256 \
  -Ddd.agent.host=datadog  \
  # -Ddd.agent.host=192.168.1.39 \
  -Ddd.logs.injection=true \
  -Ddd.dynamic.instrumentation.enabled=true \
  -Ddd.trace.sample.rate=1 \
  -Ddd.appsec.enabled=true \
  -Ddd.iast.enabled=true  \
  -Ddd.service=shop \ 	
  -Ddd.env=${USERNAME}_xApm_Instrumentation_Shop -jar app.jar

# Test Start: ENTRYPOINT java  -Dotel.resource.attributes=service.name=shop,deployment.environment=${USERNAME}2_Apm_Instrumentation_Shop -jar app.jar

