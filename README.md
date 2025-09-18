# patronesPOSA
Proyecto de implementación de patrones POSA caso SURA

Microservicio para gestión de actualizaciones de clientes con integración a Kafka.

## Tecnologías
- Java 17
- Spring Boot 3.2
- Spring Kafka
- PostgreSQL
- Maven

## Ejecutar Localmente

### 1. Iniciar Infraestructura
```bash
docker-compose up -d

docker-compose down
docker-compose logs -f

ejecutar bd
 docker exec -it cliente-postgres psql -U sura_user -d sura_clientes