# Build stage
FROM maven:3.8.8-jdk-17 AS builder
WORKDIR /app
COPY . .
RUN mvn -f WebApp/pom.xml -DskipTests package

# Run stage
FROM tomcat:11-jdk17
COPY --from=builder /app/WebApp/target/fooddelivery.war /usr/local/tomcat/webapps/fooddelivery.war
EXPOSE 8080
CMD ["catalina.sh", "run"]