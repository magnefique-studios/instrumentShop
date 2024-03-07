FROM openjdk:21
ADD target/javashop.products-latest.jar app.jar
ADD products.yml app-config.yml
EXPOSE 8020

#COPY dd-java-agent.jar  .
ADD 'https://dtdg.co/latest-java-tracer' latest-java-tracer
#ENTRYPOINT java -Ddd.instrumentation.telemetry.enabled=false  -Dotel.traces.sampler=rules -Dotel.traces.sampler.arg=drop=healthcheck -Dotel.resource.attributes=service.name=products,deployment.environment=${USERNAME}_xApm_Instrumentation_Shop -jar app.jar server app-config.yml


ENV DD_SERVICE products
ENV DD_VERSION 1.5

LABEL com.datadoghq.tags.env=${USERNAME}_xApm_Instrumentation_Shop
LABEL com.datadoghq.tags.service="products"
LABEL com.datadoghq.tags.version="1.5"

ARG DD_GIT_REPOSITORY_URL
ARG DD_GIT_COMMIT_SHA
ENV DD_GIT_REPOSITORY_URL=${DD_GIT_REPOSITORY_URL} 
ENV DD_GIT_COMMIT_SHA=${DD_GIT_COMMIT_SHA}

ENTRYPOINT java -javaagent:latest-java-tracer \
  -Ddd.profiling.enabled=true \
  -XX:FlightRecorderOptions=stackdepth=256 \
  -Ddd.dynamic.instrumentation.enabled=true \
  #-Ddd.agent.host=datadog \
#  -Ddd.agent.host=192.168.1.35 \
#  -Ddd.agent.host=${DD_AGENT_HOST} \
  -Ddd.data.streams.enabled=true \
  -Ddd.logs.injection=true \
  -Ddd.trace.sample.rate=1 \
  -Ddd.appsec.enabled=true \
  -Ddd.iast.enabled=true  \
  -Ddd.service=products \
  -Ddd.env=${USERNAME}_xApm_Instrumentation_Shop -jar app.jar server app-config.yml
