# TP Integrador · DAW1 — Sistema de Gestión de canchas

**Alumno/a:** Vargas, Sofia Leonor · **Legajo:** 510029
**Enunciado elegido:**  canchas 


## Descripción

Sistema desarrollado con Spring Boot para la administración de un complejo deportivo. Permite gestionar canchas, registrar clientes y administrar reservas evitando solapamientos de horarios y controlando el ciclo de estados.

## Cómo ejecutarlo

Requisitos: Java 21 (Maven viene incluido con `mvnw`).

```bash
./mvnw spring-boot:run
```

La API queda en `http://localhost:8080/api`. La base H2 se guarda en `data/` (no se sube al repositorio).
Consola H2: `http://localhost:8080/h2-console`.

## Modelo

| Entidad | Atributos principales | Relaciones |
|---|---|---|
| Cancha | id, nombre, deporte, precioHora | 1 a N con Reserva |
| Cliente | id, nombre, email, telefono | 1 a N con Reserva |
| Reserva | id, fecha, horaInicio, precioTotal, estado | N a 1 con Cancha y Cliente |

Estados de Reserva: `PENDIENTE → CONFIRMADA | CANCELADA`

## Endpoints

| Método | Ruta | Descripción | Respuestas |
|---|---|---|---|
| GET | /api/canchas | Lista todas las canchas | 200 |
| GET | /api/canchas/{id} | Busca cancha por id | 200, 404 |
| POST | /api/canchas | Crea una nueva cancha | 201, 400 |
| PUT | /api/canchas/{id} | Modifica los datos de una cancha | 200, 400, 404 |
| DELETE | /api/canchas/{id} | Borra una cancha | 204, 404 |
| PATCH | /api/.../{id}/estado | Cambia el estado | 200, 400, 404, 409 |
|GET |/api/canchas/{id}/reservas|Reservas de la cancha (admite ?fecha=)| 200, 404|
|POST|/api/clientes|Registra un nuevo cliente|201, 400|
|GET|/api/clientes/{id}/reservas|Lista reservas de un cliente|200, 404|
|POST|/api/reservas|Crea una reserva (estado PENDIENTE)|201, 400, 404, 409|
|PATCH|/api/reservas/{id}|Cambia el estado de una reserva|200, 400, 404, 409|

## Reglas de negocio

1. Duracion de reserva: Entre 1 y 3 horas como máximo.
2. Cálculo de precio: Se calcula automáticamente como precioHora x horas.
3. Superposición de horarios: No se permite que dos reservas ocupen la misma cancha en horarios solapados.
4. Flujo de estados: Una reserva nace en PENDIENTE. Puede pasar a CONFIRMADA o CANCELADA, pero no se pueden saltear estados ni volver atrás una confirmada.

## Pruebas

En `pruebas.http` están todas las peticiones de ejemplo (se ejecutan desde IntelliJ).
