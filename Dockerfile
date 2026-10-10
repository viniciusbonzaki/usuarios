# Definir a Imagem do Grandle que será utilizada
FROM gradle:9.5-jdk17-alpine AS build

# Diretório de Trabalho dentro do Container
WORKDIR /app

# Copiar o arquivo .jar gerado pela Build para o Diretório de Trabalho
COPY . .

# Gerar o Pacote .jar automaticamente
RUN gradle build --no-daemon

# Versão do Java
FROM eclipse-temurin:17-alpine

# Diretório de Trabalho dentro do Container
WORKDIR /app

# Gerar o arquivo .jar do Projeto na pasta Build
COPY --from=build /app/build/libs/*.jar  /app/usuarios.jar

# Definir a porta em que a Aplicação irá rodar
EXPOSE 8080

# Comando para rodar a Aplicação
CMD ["java", "-jar", "/app/usuarios.jar"]