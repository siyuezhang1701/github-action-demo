FROM openjdk:8-jdk-alpine AS builder
WORKDIR /app
COPY . .
RUN ./gradlew build

FROM builder
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080

CMD ["java", "-jar", "app.jar"]