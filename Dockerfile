FROM amazoncorretto:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} contas-pagar.jar

CMD apt-get update -y

ENTRYPOINT ["java", "-Xmx2048M", "-jar", "/contas-pagar.jar"]