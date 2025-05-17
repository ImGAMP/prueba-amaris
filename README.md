# Prueba Técnica - Fullstack Java + Angular

Este proyecto es una solución técnica para la evaluación de candidatos al rol **Java Software Engineer**, utilizando un enfoque fullstack con autenticación y consumo de APIs externas.

## Arquitectura

La solución se divide en dos aplicaciones desacopladas:

- **Backend**: Java con Spring (WAR compatible con WildFly)
- **Frontend**: Angular 17 con autenticación y búsqueda

## 🧩 Estructura general del repositorio

```plaintext
├── backend/        → API REST con Spring MVC tradicional (Java 17)
├── frontend/       → Aplicación Angular 17 (SPA)
└── docker-compose.yml
```

## 🔐 Autenticación

- Autenticación basada en **JWT**
- Endpoint de login: `POST /api/auth/login`
- Acceso protegido a rutas `/api/usuarios` y `/api/empleados`
- Angular maneja el token vía `TokenInterceptor`

## 📦 Backend (Spring MVC)

| Característica        | Detalle                                  |
|-----------------------|------------------------------------------|
| Framework             | Spring MVC clásico (WAR)                 |
| Seguridad             | Spring Security + JWT                    |
| Persistencia          | JDBC con PostgreSQL                      |
| Cliente externo       | Proxy a `https://dummy.restapiexample.com` |
| Pruebas               | JUnit + Mockito + JaCoCo                 |
| Empaquetado           | `.war` listo para WildFly                |

## 💻 Frontend (Angular 17)

| Característica        | Detalle                                  |
|-----------------------|------------------------------------------|
| Framework             | Angular 17 SPA                           |
| UI                    | Bootstrap 5.3                            |
| Funcionalidad         | Login, logout, búsqueda y CRUD local     |
| Pruebas               | Karma + Jasmine con coverage             |

## 🚀 Ejecución local con Docker

**Desarrollo recomendado:**

```bash
docker-compose down -v --remove-orphans
docker-compose build --no-cache && docker-compose up
```

| Componente | URL                          |
|------------|------------------------------|
| Frontend   | http://localhost:4200/auth/login |
| Backend    | http://localhost:8081/amaris-back/api |

## 🧪 Pruebas y cobertura

### Backend

```bash
cd backend
mvn clean verify
```

- Reporte JaCoCo: `backend/target/site/jacoco/index.html`

### Frontend

```bash
cd frontend
npm install
npm run test -- --no-watch --no-progress --browsers=ChromeHeadlessCI --code-coverage
```

- Reporte: `frontend/coverage/index.html`

## 📄 Credenciales de prueba

```txt
Usuario: admin
Contraseña: 1234
```

## 👨‍💻 Autor

**Gustavo Adolfo Mojica Perdigón**  
📧 gmojica@unal.edu.co  
🗓️ 2025