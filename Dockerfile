FROM node:14 AS build_frontend
WORKDIR /app/frontend
COPY frontend/package*.json ./
RUN npm install
COPY frontend/ .
RUN npm run build

FROM maven:3.8.5-openjdk-17 AS build_backend
WORKDIR /app/backend
COPY backend .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=build_backend /app/target/movie-web-application-0.0.1-SNAPSHOT.jar movie-web-application.jar
COPY --from=build_frontend /app/frontend/build /app/frontend/build
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "movie-web-application.jar"]