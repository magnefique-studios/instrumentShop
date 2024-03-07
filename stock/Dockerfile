FROM openjdk:15
ADD target/javashop.stock-latest.jar app.jar
EXPOSE 8030
ADD 'https://dtdg.co/latest-java-tracer' dd-java-agent.jar


ENV DD_SERVICE stock
ENV DD_VERSION 1.4

LABEL com.datadoghq.tags.service="stock"
LABEL com.datadoghq.tags.version="1.4"
LABEL com.datadoghq.tags.env=${USERNAME}_xApm_Instrumentation_Shop

ARG DD_GIT_REPOSITORY_URL
ARG DD_GIT_COMMIT_SHA
ENV DD_GIT_REPOSITORY_URL=${DD_GIT_REPOSITORY_URL} 
ENV DD_GIT_COMMIT_SHA=${DD_GIT_COMMIT_SHA}


#COPY dd-java-agent.jar  .

ENTRYPOINT java -javaagent:dd-java-agent.jar \
  -Ddd.profiling.enabled=true \
  -XX:FlightRecorderOptions=stackdepth=256 \
 # -Ddd.agent.host=${DD_AGENT_HOST} \
 #  -Ddd.agent.host=192.168.1.35 \ 
  -Ddd.data.streams.enabled=true \
  -Ddd.agent.host=datadog \
  -Ddd.logs.injection=true \
  -Ddd.trace.sample.rate=1 \
  -Ddd.appsec.enabled=true \
  -Ddd.iast.enabled=true  \
  -Ddd.service=stock \
  -Ddd.env=${USERNAME}_xApm_Instrumentation_Shop -jar app.jar


