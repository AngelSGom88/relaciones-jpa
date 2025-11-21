# Relaciones JPA con Spring Boot

Proyecto didáctico que muestra distintas relaciones JPA/Hibernate (1–1, 1–N, N–M, uni y bidireccionales) sobre una API REST con Spring Boot 3 y H2 embebida.  
Incluye semillas de datos, AOP para métricas y logging, manejo de errores con ProblemDetails y un cliente HTML para probar la API.

## Tecnologías

- Java 17
- Spring Boot 3.5.6
    - spring-boot-starter-web
    - spring-boot-starter-data-jpa
    - spring-boot-starter-validation
    - spring-boot-starter-aop
- Base de datos: H2 en memoria
- Maven
- Cliente de pruebas: `app.html` (vanilla JS + fetch)

## Relaciones implementadas

Cada variante vive en su propio paquete bajo `dev.angel.relaciones`:

| Tipo relación            | Paquete            | Descripción                               | Endpoints principales                                  |
|--------------------------|--------------------|-------------------------------------------|---------------------------------------------------------|
| 1–1 Bidireccional        | `_1_1_bidirec`     | PersonaBI ↔ DniBI (mappedBy)              | `/r11bi/personas`                                       |
| 1–1 Unidireccional       | `_1_1_unidirec`    | PersonaUni → DniUni                       | `/r11uni/personas`                                      |
| 1–N Bidireccional        | `_1_n_bidirec`     | Persona ↔ Direccion                       | `/r1nbi/personas`                                       |
| 1–N Unidireccional A     | `_1_n_unidirec_a`  | Persona → Direccion                       | `/r1nuni-a/personas`                                    |
| 1–N Unidireccional B     | `_1_n_unidirecc_b` | Direccion → Persona                       | `/r1nuni-b/personas`, `/r1nuni-b/direcciones`          |
| N–M Bidireccional        | `_n_m_bidirec`     | Persona ↔ Proyecto                        | `/rnmbi/personas`, `/rnmbi/proyectos`                  |
| N–M Unidireccional       | `_n_m_unidirecc`   | Persona → Proyecto                        | `/rnmuni/personas`, `/rnmuni/proyectos`                |

## Estructura del proyecto

```
src/main/java/dev/angel/relaciones
 ├── _1_1_bidirec
 │    ├── config/         # SecurityConfig, aspectos AOP (logging, timing)
 │    ├── domain/         # Entidades JPA (PersonaBI, DniBI…)
 │    ├── repository/     # Repositorios Spring Data JPA
 │    ├── service/        # Lógica de negocio
 │    ├── web/            # Controladores REST
 │    └── SeedR11Bi.java  # Datos de ejemplo
 ├── _1_1_unidirec
 ├── _1_n_bidirec
 ├── _1_n_unidirec_a
 ├── _1_n_unidirec_b
 ├── _n_m_unidirecc
 └── exception
      └── ApiExceptionHandler.java   # Manejo global (ProblemDetails)
```

## Puntos destacables

- Diseño por features: cada relación tiene su propio módulo completo.
- Seeds de datos: “Ana”, “Luis”, etc. se cargan al arrancar.
- AOP:
    - `ServiceTimingAspect`: mide tiempos.
    - `RepoDmlAspect`: log de operaciones DML.
- Manejo de errores:
    - `ApiExceptionHandler` devuelve ProblemDetails (400/404/409).
- Seguridad: API abierta (`permitAll`).
- Cliente HTML (`app.html`) para probar todas las relaciones.

## Puesta en marcha

### Requisitos

- JDK 17
- Maven 3.x

### Ejecutar

```
mvn clean spring-boot:run
```

La aplicación estará disponible en:

```
http://localhost:8080
```

### H2 Console

```
http://localhost:8080/h2-console
```

## Cliente HTML

Abrir:

```
http://localhost:8080/app.html
```

Permite probar CRUD, vínculos y desvínculos entre entidades.

## Ejemplos de endpoints

```
GET /rnmbi/personas
POST /rnmbi/personas
POST /rnmbi/personas/{personaId}/proyectos/{proyectoId}
GET /r1nuni-b/direcciones
```

## Logging y observabilidad

- `ServiceTimingAspect`: tiempos de servicios
- `RepoDmlAspect`: operaciones DML
- Logs rotativos configurados en `logback-spring.xml`

## Objetivo del proyecto

Servir como material de aprendizaje para comprender diferentes relaciones JPA, su impacto en el JSON, la navegabilidad y las buenas prácticas REST (validación, seeds, manejo de errores, AOP…).