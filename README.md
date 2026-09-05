# ms-campuslab-catalog

Microservicio de catálogo de CampusLab.

## Tecnologías

- Java
- Spring Boot
- Spring Data JPA
- Spring Security
- Oracle

## Recursos

- Laboratorios.
- Equipos.
- Insumos.

## Funcionalidades

- Listar recursos.
- Consultar recursos por ID.
- Crear recursos.
- Actualizar recursos.
- Administrar stock.
- Administrar cupos.

## Endpoints

```http
GET /api/catalog/resources
GET /api/catalog/resources/{id}
POST /api/catalog/resources
PUT /api/catalog/resources/{id}
```

## Reglas de negocio

- El stock o cupo disminuye al aprobar una reserva.
- ADMIN puede crear y modificar recursos.
- ADMIN y OPERATOR pueden consultar recursos.

## Roles

```text
ADMIN
OPERATOR
```

## Base de datos

Oracle.

## Variables de entorno

```env
DB_URL=
DB_USERNAME=
DB_PASSWORD=
```

## Ejecución local

```bash
./mvnw spring-boot:run
```

## Build

```bash
./mvnw clean package
```
