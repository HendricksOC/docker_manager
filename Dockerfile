FROM eclipse-temurin:21-jre

# Cria um grupo e um usuário sem privilégios de root
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser

WORKDIR /app

# Copia o jar já atribuindo a posse ao novo usuário
COPY --chown=appuser:appgroup target/*.jar app.jar

# Troca para o usuário não-root antes de iniciar a aplicação
USER appuser

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]