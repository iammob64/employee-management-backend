FROM eclipse-temurin:21

WORKDIR /app

COPY . .

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/employee-management-system-1.0.0-SNAPSHOT.jar"]