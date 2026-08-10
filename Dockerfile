# 1. Build stage
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY pom.xml .
# Faqat dependency'larni yuklab olish (keshlash uchun)
RUN mvn dependency:go-offline -B
COPY src ./src
# Ilovani build qilish (-DskipTests testlarni o'tkazib yuborish uchun agar CI da ishlasa, hozircha o'chirib qo'yish ham mumkin)
RUN mvn clean package -DskipTests

# 2. Run stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

# JVM sozlamalari
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75.0"

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
