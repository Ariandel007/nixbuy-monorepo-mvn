FROM maven:3.8.5-openjdk-17 as builder

WORKDIR /build

# Copy the parent POM first
COPY pom.xml .

# Copy all module POMs first (this is crucial for Maven to recognize the project structure)
COPY commons/pom.xml commons/
COPY user-service/pom.xml user-service/
COPY api-gateway-service/pom.xml api-gateway-service/
COPY payment-service/pom.xml payment-service/
COPY products-service/pom.xml products-service/
COPY products-service/application/pom.xml products-service/application/
COPY products-service/infrastructure/pom.xml products-service/infrastructure/
COPY products-service/domain/pom.xml products-service/domain/

# Now copy the source files for each module
COPY commons/src commons/src/
COPY user-service/src user-service/src/
COPY api-gateway-service/src api-gateway-service/src/
COPY payment-service/src payment-service/src/
COPY products-service/domain/src products-service/domain/src/
COPY products-service/application/src products-service/application/src/
COPY products-service/infrastructure/src products-service/infrastructure/src/

# Build the entire project
RUN mvn -q -B clean install -DskipTests \
    -Dspring-boot.repackage.skip=true \
    -pl \!products-service/infrastructure && \
    mvn -q -B clean package -DskipTests \
    -pl products-service/infrastructure

FROM openjdk:17-jdk-alpine as imageToExecute

WORKDIR /app
COPY --from=builder /build/products-service/infrastructure/target/*.jar /app/app.jar
CMD ["java", "-jar", "app.jar"]


# Environment variables:
# PORT, BD_USERNAME, BD_URL, BD_PASSWORD, DDL_AUTO, WEBCLIENT_URL, PRODUCTS_URL, STRIPE_API_KEY, STRIPE_WEBHOOK_SECRET

# Command to build: docker build -t products-service-nixbuy:0.0.1-SNAPSHOT -f ./products-service/Dockerfile .


# Command to run:
# docker run -d --name products-service-nixbuy \
#   --network local-dev \
#   --memory="360m" \
#   --log-opt max-size=250m --log-opt max-file=3 \
#   -e PORT=<your-port>  \
#   -e BD_USERNAME=<your-db-username> \
#   -e BD_URL=<your-db-url> \
#   -e BD_PASSWORD=<your-db-password> \
#   -e DDL_AUTO=none \
#   -e KAFKA_BOOTSTRAP_SERVERS=<kafka-bootstrap-servers> \
#   -e TRACING_URL_ENDPOINT=<tracing-url> \
#   -e CLOUDINARY_CLOUD_NAME=<cloudinary-cloud-name> \
#   -e CLOUDINARY_API_KEY=<cloudinary-api-key> \
#   -e CLOUDINARY_SECRET=<cloudinary-secret> \
#   -p <host-port>:<container-port> products-service-nixbuy:0.0.1-SNAPSHOT