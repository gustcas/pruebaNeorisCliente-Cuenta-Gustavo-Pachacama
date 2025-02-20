# Imagen base de OpenJDK
FROM openjdk:17-jdk-slim

# Instalar Maven
RUN apt update && apt install -y maven

WORKDIR /app

# Copiar el código fuente al contenedor
COPY . .

# Construir el JAR dentro del contenedor
RUN mvn clean package -DskipTests

# Copiar el JAR generado
COPY target/cliente-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto del microservicio
EXPOSE 8081

# Comando para ejecutar la aplicación
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/urandom","-jar","app.jar"]