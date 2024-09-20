#We define and arg for many steps
ARG MODULE_NAME=user-service

FROM maven:3.8.5-openjdk-17 as builder
#We are using the first ARG:
ARG MODULE_NAME
WORKDIR /app/$MODULE_NAME

COPY ./commons/pom.xml /app/commons/pom.xml
COPY ./commons/src /app/commons/src

COPY ./api-gateway-service/pom.xml /app/api-gateway-service/pom.xml
COPY ./api-gateway-service/src /app/api-gateway-service/src

COPY ./payment-service/pom.xml /app/payment-service/pom.xml
COPY ./payment-service/src /app/payment-service/src

COPY ./products-service/pom.xml /app/products-service/pom.xml
COPY ./products-service/application/pom.xml /app/products-service/application/pom.xml
COPY ./products-service/application/src /app/products-service/application/src
COPY ./products-service/infrastructure/pom.xml /app/products-service/infrastructure/pom.xml
COPY ./products-service/infrastructure/src /app/products-service/infrastructure/src
COPY ./products-service/domain/pom.xml /app/products-service/domain/pom.xml
COPY ./products-service/domain/src /app/products-service/domain/src


COPY ./pom.xml /app
COPY ./$MODULE_NAME/pom.xml .
COPY ./$MODULE_NAME/src ./src

#
    # RUN mvn -f /app/commons/pom.xml clean install
RUN mvn -f /app/pom.xml clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

RUN mvn -f /app/pom.xml clean package

FROM openjdk:17-jdk-alpine as imageToExecute
#We are using the first ARG:
ARG MODULE_NAME
WORKDIR /app
ARG FOLDER_FROM_BUILDER=/app/$MODULE_NAME/target
COPY --from=builder $FOLDER_FROM_BUILDER/*.jar /app/app.jar

CMD ["java", "-jar", "app.jar"]

# Environment variables:
# PORT, BD_USERNAME, BD_URL, BD_PASSWORD, DDL_AUTO, ZIPKIN_TRACING_URL_ENDPOINT,
# CLOUDINARY_CLOUD_NAME, CLOUDINARY_API_KEY, CLOUDINARY_SECRET

# Command to build: docker build -t nixbuy-user-service:0.0.1-SNAPSHOT -f ./user-service/Dockerfile .


