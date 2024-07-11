FROM amazoncorretto:17

ARG PROFILE
ARG ADDITIONAL_OPTS

ENV PROFILE=${PROFILE}
ENV ADDITIONAL_OPTS=${ADDITIONAL_OPTS}

WORKDIR /opt/contas_pagar

COPY /target/contas-pagar*.jar contas-pagar.jar

SHELL ["/bin/sh", "-c"]

EXPOSE 5005
EXPOSE 8080

CMD java ${ADDITIONAL_OPTS} -jar contas-pagar.jar --spring.profiles.active=${PROFILE}