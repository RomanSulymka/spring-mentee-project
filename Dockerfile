FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/spring-mentee-project-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

FROM postgres:13.3
ENV POSTGRES_PASSWORD=mysecretpassword
ENV POSTGRES_USER=postgres
ENV POSTGRES_DB=mywallet
COPY create-db.sql /docker-entrypoint-initdb.d/
EXPOSE 5432
CMD ["postgres"]
