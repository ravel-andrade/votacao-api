FROM openjdk:17

ARG PROFILE
ARG ADITIONAL_OPTS

ENV PROFILE=${PROFILE}
ENV ADITIONAL_OPTS=${ADDITIONAL_OPTS}

WORKDIR opt/votacao

SHELL ["/bin/sh", "-c"]

EXPOSE 5005
EXPOSE 8080

CMD java ${ADITIONAL_OPTS} -jar /build/libs/votacao-api-0.0.1-SNAPSHOT.jar --spring.profiles.active=${PROFILE}