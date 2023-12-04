FROM openjdk:17-alpine

COPY target/picpay-backend-challenge-*.jar /app/app.jar

WORKDIR /app

EXPOSE 8090

CMD ["java", "-jar", "app.jar"]