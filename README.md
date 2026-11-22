# ms-campuslab-catalog

Microservicio de catálogo de CampusLab. Persiste recursos y su disponibilidad
en Oracle.

## Recursos y endpoints internos

Los tipos de recurso son `LAB`, `EQUIPMENT` y `SUPPLY`.

| Método | Ruta | Uso |
|---|---|---|
| `GET` | `/api/v1/catalog/resources` | Listar recursos; filtro opcional `type` |
| `GET` | `/api/v1/catalog/resources/{id}` | Consultar un recurso |
| `POST` | `/api/v1/catalog/resources` | Crear un recurso |
| `PUT` | `/api/v1/catalog/resources/{id}` | Actualizar un recurso |
| `POST` | `/api/v1/catalog/resources/{id}/availability/reserve` | Reservar un cupo |
| `POST` | `/api/v1/catalog/resources/{id}/availability/release` | Liberar un cupo con `Idempotency-Key` |

Ejemplo:

```http
GET /api/v1/catalog/resources?type=EQUIPMENT
```

## Disponibilidad

Bookings reserva el cupo al crear la solicitud, antes de persistirla en estado
`REQUESTED`. Si la creación falla, Bookings intenta compensar liberando el
cupo. Una cancelación o devolución también libera la disponibilidad. La
liberación requiere un `Idempotency-Key` estable por reserva; repetir la misma
clave no vuelve a incrementar el cupo.

## Autenticación

Catalog no implementa autenticación ni autorización HTTP propia. Los roles de
las rutas públicas se aplican en el BFF: `ADMIN` crea y actualiza; `ADMIN` y
`OPERATOR` consultan por ID; `ADMIN`, `OPERATOR` y `CLIENT` pueden listar.
Las rutas de disponibilidad son llamadas internas de Bookings.

## Configuración, ejecución y pruebas

```env
DB_URL=
DB_USERNAME=
DB_PASSWORD=
```

```bash
./mvnw spring-boot:run
./mvnw clean package
```

La suite predeterminada ejecuta pruebas unitarias sin Oracle. La prueba de
integración del contexto es opt-in y requiere una base Oracle disponible:

```bash
./mvnw -DRUN_CATALOG_ORACLE_INTEGRATION_TESTS=true test
```

El contrato de las rutas internas está en
[`openapi.yaml`](./openapi.yaml).
