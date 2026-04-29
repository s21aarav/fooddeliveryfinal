FROM tomcat:11-jdk17

# Use the built WAR produced by the Maven build
ARG WAR_FILE=WebApp/target/fooddelivery.war
COPY ${WAR_FILE} /usr/local/tomcat/webapps/fooddelivery.war

# Let Render or the environment provide DB host/creds. The app reads FOODDB_* env vars.
ENV FOODDB_URL="jdbc:mysql://$DB_HOST:$DB_PORT/$DB_NAME"
ENV FOODDB_USER=$DB_USER
ENV FOODDB_PASSWORD=$DB_PASSWORD

EXPOSE 8080

CMD ["catalina.sh", "run"]
