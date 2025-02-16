FROM maven:3.8.5-openjdk-17 as builder

WORKDIR /build

COPY pom.xml pom.xml
COPY commons/pom.xml commons/pom.xml
COPY user-service/pom.xml user-service/pom.xml
COPY api-gateway-service/pom.xml api-gateway-service/pom.xml
COPY payment-service/pom.xml payment-service/pom.xml
COPY products-service/pom.xml products-service/pom.xml
COPY products-service/application/pom.xml products-service/application/pom.xml
COPY products-service/infrastructure/pom.xml products-service/infrastructure/pom.xml
COPY products-service/domain/pom.xml products-service/domain/pom.xml

# Resolve dependencies for `common` module, e.g., shared libraries
# Also build all the required projects needed by the common module.
# In this case, it will also resolve dependencies for the `root` module.
RUN mvn -q -ntp -B -pl commons -am dependency:go-offline

# Copy full sources for `commons` module
COPY commons commons
# Install the commons module in the local Maven repo (`.m2`)
# This will also install the `root` module.
# See: `la -lat ~/.m2/repository/org/example/*/*`
RUN mvn -q -B -pl commons install
# Resolve dependencies for the main application
RUN mvn -q -ntp -B -pl products-service/infrastructure -am dependency:go-offline
# Copy sources for main application
COPY products-service products-service
# Package the common and application modules together. We are going to skip tests becuase of testcontainers(and using docker in docker is still not viable)
RUN mvn -q -ntp -B -pl commons,products-service/domain,products-service/application,products-service/infrastructure package -DskipTests

FROM openjdk:17-jdk-alpine as imageToExecute
#We are using the first ARG:
ARG MODULE_NAME
WORKDIR /app
COPY --from=builder /build/products-service/infrastructure/target/*.jar /app/app.jar
CMD ["java", "-jar", "app.jar"]

# Environment variables:
# PORT, BD_USERNAME, BD_URL, BD_PASSWORD, DDL_AUTO, WEBCLIENT_URL, PRODUCTS_URL, STRIPE_API_KEY, STRIPE_WEBHOOK_SECRET

# Command to build: docker build -t payment-service-nixbuy:0.0.1-SNAPSHOT -f ./payment-service/Dockerfile .

# Command to run:
# docker run -d --name products-service-nixbuy \
# -e PORT=<your-port> \
# -e BD_USERNAME=<your-db-username> \
# -e BD_URL=<your-db-url> \
# -e BD_PASSWORD=<your-db-password> \
# -e DDL_AUTO=<ddl-auto-option> \
# -e KAFKA_BOOTSTRAP_SERVERS=<kafka-bootstrap-servers> \
# -e TRACING_URL_ENDPOINT=<tracing-url> \
# -e CLOUDINARY_CLOUD_NAME=<cloudinary-cloud-name> \
# -e CLOUDINARY_API_KEY=<cloudinary-api-key> \
# -e CLOUDINARY_SECRET=<cloudinary-secret> \
# -p <host-port>:<container-port> products-service-nixbuy:0.0.1-SNAPSHOT
# --network local-dev
# --memory="360m"
# --log-opt max-size=250m --log-opt max-file=3