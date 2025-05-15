# Prueba Técnica - Fullstack Java + Angular

Este proyecto es una solución técnica para la evaluación de candidatos al rol **Java Software Engineer**, utilizando un enfoque fullstack con autenticación y consumo de APIs externas.

## Arquitectura

La solución se divide en dos aplicaciones desacopladas:

- **Backend**: Java con Spring Boot y JWT (packaging WAR)
- **Frontend**: Angular 17 SSR con autenticación y consumo del backend

## 🧩 Estructura general del repositorio

```plaintext
├── backend/        → API REST con Spring Boot (Java 17)
├── frontend/       → Aplicación Angular 17 (SSR-ready)
└── docker-compose.yml

```

## 🔐 Autenticación

- Autenticación basada en **JWT**
- Endpoint de login: `POST /api/auth/login`
- Acceso protegido a rutas `/api/usuarios` y `/api/empleados`
- Angular maneja el token vía `TokenInterceptor`

## 📦 Backend (Spring Boot)

| Característica        | Detalle                                  |
|-----------------------|------------------------------------------|
| Framework             | Spring Boot 3.2.4                         |
| Seguridad             | Spring Security + JWT                    |
| Persistencia          | JPA + PostgreSQL                         |
| Cliente externo       | Consumo de `https://dummy.restapiexample.com` |
| Pruebas               | JUnit + Mockito (alta cobertura con JaCoCo) |
| Empaquetado           | `.war` listo para WildFly o Docker       |

## 💻 Frontend (Angular 17)

| Característica        | Detalle                                  |
|-----------------------|------------------------------------------|
| Framework             | Angular v19.2                            |
| SSR                   | Compatible con Server Side Rendering     |
| UI                    | Bootstrap 5.3                            |
| Protección de rutas   | `auth.guard.ts`                          |
| Logout                | Botón funcional en navbar                |
| Pruebas               | Karma + Jasmine (13 pruebas exitosas)    |

## 🚀 Ejecución local

### Requisitos

- Docker y Docker Compose
- Java 17 + Maven
- Node.js + Angular CLI

### Levantar todo con Docker

```bash
docker compose up --build
```

Backend disponible en: `http://localhost:8080`  
Frontend disponible en: `http://localhost:4200`

### Correr backend manualmente

```bash
cd backend
./mvnw clean package
java -jar target/prueba-amaris.war
```

### Correr frontend manualmente

```bash
cd frontend
npm install
ng serve
```

## 🧪 Pruebas

### Backend

```bash
cd backend
./mvnw test
```

- JaCoCo habilitado (`target/site/jacoco/index.html`)

### Frontend

```bash
cd frontend
ng test
```

- Ejecuta 13 pruebas unitarias usando `ChromeHeadless`

## 🧰 Endpoints clave

- `POST /api/auth/login` → Autenticación con JSON `{username, password}`
- `GET /api/auth/me` → Usuario actual (JWT)
- `GET /api/usuarios` → Listado de usuarios
- `GET /api/empleados` → Proxy a dummy.restapiexample.com

## 📌 Características adicionales

- Patrón JSON:API en todas las respuestas
- Alto cumplimiento de principios SOLID
- Logs seguros con Spring Boot Actuator desactivado por defecto
- CORS configurado para `localhost:4200`

## 📄 Credenciales de prueba

```txt
Usuario: admin
Contraseña: 1234
```

## 🧠 Futuras mejoras

- Refactor a microservicios independientes
- Agregar API Gateway y Service Discovery
- Añadir Redis o caché intermedio
- Monitorización Prometheus + Grafana

---

## 👨‍💻 Autor

**Gustavo Adolfo Mojica Perdigón**  
📧 gmojica@unal.edu.co  
🗓️ 2025