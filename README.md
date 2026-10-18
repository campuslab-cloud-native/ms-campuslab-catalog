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
GET /api/v1/catalog/resources
GET /api/v1/catalog/resources/{id}
POST /api/v1/catalog/resources
PUT /api/v1/catalog/resources/{id}
```

Para liberar un cupo asociado a una reserva, el backend de reservas envía:

```http
POST /api/v1/catalog/resources/{id}/availability/release
Idempotency-Key: booking:{bookingId}
```

## Reglas de negocio

- El stock o cupo disminuye al aprobar una reserva.
- Liberar un cupo requiere enviar `Idempotency-Key` con un identificador estable
  de la reserva. Repetir la liberación con la misma clave no vuelve a incrementar
  la disponibilidad.
- `GET /api/v1/catalog/resources` solo consulta; la liberación no debe usarse para
  mostrar ni refrescar cupos.
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
