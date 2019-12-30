FROM openjdk:8
EXPOSE 8100
ADD target/currency-exchange-service.jar currency-exchange-service.jar
ENTRYPOINT ["java","-jar","currency-exchange-service.jar"]