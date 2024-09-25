FROM openjdk:17-jdk-alpine

# Устанавливаем рабочую директорию
WORKDIR /app

RUN mkdir "photo"

# Копируем собранный JAR файл из предыдущего этапа
COPY target/TgBot-0.0.1-SNAPSHOT.jar myapp.jar

# Открываем порт, на котором будет работать приложение
EXPOSE 8093

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "myapp.jar"]