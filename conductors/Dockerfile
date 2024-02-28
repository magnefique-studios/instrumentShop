FROM openjdk:21
ADD target/javashop.conductors-latest.jar app.jar
ADD conductors.yml app-config.yml
EXPOSE 8050

#COPY dd-java-agent.jar  .
#ADD 'https://dtdg.co/latest-java-tracer' dd-java-agent.jar

#ADD https://s3.amazonaws.com/dd-agent/scripts/install_script_docker_injection.sh install_script_docker_injection.sh

#RUN sudo bash -c ./install_script_docker_injection.sh




#ENTRYPOINT java -Ddd.instrumentation.telemetry.enabled=false  -Dotel.traces.sampler=rules -Dotel.traces.sampler.arg=drop=healthcheck -Dotel.resource.attributes=service.name=conductors,deployment.environment=${USERNAME}_xApm_Instrumentation_Shop -jar app.jar server app-config.yml


ENV DD_SERVICE conductors
ENV DD_VERSION 1.4

LABEL com.datadoghq.tags.service="conductors"
LABEL com.datadoghq.tags.version="1.4"

ARG DD_GIT_REPOSITORY_URL
ARG DD_GIT_COMMIT_SHA
ENV DD_GIT_REPOSITORY_URL=${DD_GIT_REPOSITORY_URL} 
ENV DD_GIT_COMMIT_SHA=${DD_GIT_COMMIT_SHA}


ENTRYPOINT java  -jar app.jar server app-config.yml

