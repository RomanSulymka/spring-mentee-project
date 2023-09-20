FROM openjdk:17
EXPOSE 8080
ADD target/spring-mentee-project-0.0.1-SNAPSHOT.jar spring-mentee-project
ENTRYPOINT ["java","-jar","/spring-mentee-project"]

FROM postgres
ENV POSTGRES_USER romansulymka
ENV POSTGRES_PASSWORD admin
ENV POSTGRES_DB mywallet
COPY create-db.sql /spring-mentee-project/