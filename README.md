# patronesPOSA
Proyecto de implementación de patrones POSA caso SURA

Microservicio para gestión de actualizaciones de clientes con integración a Kafka.

## Tecnologías
- Java 17
- Spring Boot 3.2
- Spring Kafka
- PostgreSQL
- Maven

## Enlaces de interés

Verificador estado del microservicio:
- http://localhost:8080/actuator/health

Metricas disponibles:
- http://localhost:8080/actuator/prometheus

Analizar logs:
- docker-compose logs -f

Bajar docker-compose:
- docker-compose down

Remover imágenes y volúmenes:
- docker-compose down --rmi all -v

## Ejecutar Localmente

### 1. Iniciar Infraestructura
```bash
docker-compose up -d
```

### 2. Iniciar Microservicio
```bash
./mvnw spring-boot:run
```
### 3. Probar Endpoints

Swagger UI:
- http://localhost:8080/swagger-ui/index.html

### 4. Acceder Base de datos PostgreSQL
```bash
docker exec -it cliente-postgres psql -U sura_user -d sura_clientes
```

### 5. Acceder Grafana
- http://localhost:3000 (usuario: admin, contraseña: admin123)

### 6. Detener Infraestructura
```bash
docker-compose down
```
## Notas
- Asegúrate de tener Docker y Docker Compose instalados en tu máquina.
- Verifica que los puertos 8080, 5433, 9092, 3000 y 9090 estén libres antes de iniciar los servicios.
- Puedes personalizar las configuraciones en el archivo `docker-compose.yml` según tus necesidades.
- Para cualquier duda o problema, revisa los logs del contenedor correspondiente.