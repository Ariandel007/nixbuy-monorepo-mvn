# English

This is a project with the only intention to do a mono repo of microservices which each one uses different architectures approaches.

## Tech
The following is being used:
- Maven
- Spring Boot
- Spring JPA
- Resilence4j
- Zipkin
- Debezium
- PostgreSQL
- Spring Cloud Gateway
- Apache Kafka
- Rest Assured
- Junit
- TestContainers

## How to  build

### Api Gateway Service
In the root of the project run the following command:
```bash
docker build -t gateway-service-nixbuy:0.0.1-SNAPSHOT -f ./api-gateway-service/Dockerfile .
```
Then for starting the image, run the following command:
```bash
docker run -d --name gateway-service-nixbuy \
--network local-dev \
--memory="360m" \
--log-opt max-size=250m --log-opt max-file=3 \
-e PORT=<your-port> \
-e URI_USER_SERVICE=<uri-user-service> \
-e URI_PRODUCTS_SERVICE=<uri-products-service> \
-e URI_PAYMENT_SERVICE=<uri-payment-service> \
-e TRACING_URL_ENDPOINT=<your-db-url> \
-p <host-port>:<container-port> gateway-service-nixbuy:0.0.1-SNAPSHOT
```

### Payment Service
In the root of the project run the following command:
```bash
docker build -t payment-service-nixbuy:0.0.1-SNAPSHOT -f ./payment-service/Dockerfile .
```
Then for starting the image, run the following command:
```bash
docker run -d --name payment-service-nixbuy \
  --network local-dev \
  --memory="360m" \
  --log-opt max-size=250m --log-opt max-file=3 \
  -e PORT=<your-port> \
  -e BD_USERNAME=<your-db-username> \
  -e BD_URL=<your-db-url> \
  -e BD_PASSWORD=<your-db-password> \
  -e DDL_AUTO=<ddl-auto-option> \
  -e WEBCLIENT_URL=<webclient-url> \
  -e PRODUCTS_URL=<products-url> \
  -e STRIPE_API_KEY=<products-url> \
  -e STRIPE_WEBHOOK_SECRET=<products-url> \
  -e TRACING_URL_ENDPOINT=<tracing-url> \
  -e KAFKA_BOOTSTRAP_SERVERS=<kafka-boostrap-servers> \
  -p <host-port>:<container-port> payment-service-nixbuy:0.0.1-SNAPSHOT  
```

### Products Service
In the root of the project run the following command:
```bash
docker build -t products-service-nixbuy:0.0.1-SNAPSHOT -f ./products-service/Dockerfile .
```
Then for starting the image, run the following command:
```bash
docker run -d --name products-service-nixbuy \
  --network local-dev \
  --memory="360m" \
  --log-opt max-size=250m --log-opt max-file=3 \
  -e PORT=<your-port>  \
  -e BD_USERNAME=<your-db-username> \
  -e BD_URL=<your-db-url> \
  -e BD_PASSWORD=<your-db-password> \
  -e DDL_AUTO=none \
  -e KAFKA_BOOTSTRAP_SERVERS=<kafka-bootstrap-servers> \
  -e TRACING_URL_ENDPOINT=<tracing-url> \
  -e CLOUDINARY_CLOUD_NAME=<cloudinary-cloud-name> \
  -e CLOUDINARY_API_KEY=<cloudinary-api-key> \
  -e CLOUDINARY_SECRET=<cloudinary-secret> \
  -p <host-port>:<container-port> products-service-nixbuy:0.0.1-SNAPSHOT
```

### User Service
In the root of the project run the following command:
```bash
docker build -t user-service-nixbuy:0.0.1-SNAPSHOT -f ./user-service/Dockerfile .
```
Then for starting the image, run the following command:
```bash
docker run -d --name user-service-nixbuy \
  --network local-dev \
  --memory="360m" \
  --log-opt max-size=250m --log-opt max-file=3 \
  -e PORT=<port> \
  -e BD_USERNAME=<username-bd> \
  -e BD_URL=<bd-url> \
  -e BD_PASSWORD=<bd-password> \
  -e DDL_AUTO=none \
  -e TRACING_URL_ENDPOINT=<jagger-url-endpoint> \
  -e CLOUDINARY_CLOUD_NAME=<cloudinary-name> \
  -e CLOUDINARY_API_KEY=<cloudinary-api-key \
  -e CLOUDINARY_SECRET=<cloudinary-secret> \
  -p <host-port>:<container-port> user-service-nixbuy:0.0.1-SNAPSHOT
```
