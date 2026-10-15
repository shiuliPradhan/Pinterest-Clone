# Pinterest Clone
A full-stack visual discovery app inspired by Pinterest. Users can sign up, browse a masonry feed, create and save pins, organize boards, follow other people, and explore business showcases and sponsored pins.
This capstone is split into two apps:
| Folder | Role |
| --- | --- |
| `pinterest_frontend` | React + TypeScript UI (Create React App) |
| `pinterest_backend` | Spring Boot REST API with JWT auth and MySQL |
---
## Features
**Accounts**
- Sign up, sign in, logout
- Forgot / reset password
- JWT-protected routes
- Login attempt tracking / lockout support
**Pins and boards**
- Home feed with masonry layout
- Create, view, edit, and delete pins
- Save pins, like pins, attach media and tags
- Boards with collaborator invitations
**Social**
- Follow / unfollow
- Followers and following lists
- Notifications (frontend + WebSocket client)
**Discovery**
- Explore and keyword search
- Search suggestions
**Business**
- Business profiles, campaigns, showcases
- Sponsored pins (can be toggled from the frontend env)
---
## Tech stack
**Frontend**
- React 18, TypeScript, React Router
- Tailwind CSS, Bootstrap, Headless UI, Lucide icons
- Axios, Zustand, TanStack Query
- Infinite scroll for the feed
**Backend**
- Java 17, Spring Boot 3.5
- Spring Security, Spring Data JPA, Bean Validation
- JWT (`jjwt`)
- MySQL
- Log4j2
- OpenAPI / Swagger UI (`/swagger`)
---
## Prerequisites
- Node.js 18+ and npm
- JDK 17
- Maven 3.8+
- MySQL 8
---
## Project structure
```
Pinterest_Clone/
├── pinterest_frontend/     # React UI
│   └── src/
│       ├── api/            # Axios clients
│       ├── components/     # Pins, boards, auth, layout
│       ├── context/        # Auth, UI, notifications
│       ├── pages/          # Route screens
│       └── routes/         # AppRoutes
└── pinterest_backend/      # Spring Boot API
    └── src/main/java/com/infy/pinterest/
        ├── controller/     # REST endpoints
        ├── service/        # Business logic
        ├── repository/     # JPA repositories
        ├── entity/         # Persistence models
        └── dto/            # Request/response DTOs
```
---

## Backend setup
1. Create (or let Hibernate create) a MySQL database named `pinterest_db`.
2. Edit `pinterest_backend/src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/pinterest_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=YOUR_MYSQL_USER
spring.datasource.password=YOUR_MYSQL_PASSWORD
jwt.secret=REPLACE_WITH_A_LONG_RANDOM_SECRET
jwt.expiration=86400000
server.port=8080
```
3. From `pinterest_backend`:
```bash
mvn spring-boot:run
```
The API runs at [http://localhost:8080](http://localhost:8080).  
Swagger UI: [http://localhost:8080/swagger](http://localhost:8080/swagger)
Hibernate is set to `ddl-auto=update`, so tables are created/updated on startup.
---
## Frontend setup
1. From `pinterest_frontend`, create a `.env` file (do not commit secrets):
```env
REACT_APP_API_URL=http://localhost:8080/
REACT_APP_WS_URL=ws://localhost:8080/ws
REACT_APP_ENABLE_SPONSORED=true
```
2. Install and start:
```bash
cd pinterest_frontend
npm install
npm start
```
The UI runs at [http://localhost:3000](http://localhost:3000). Unauthenticated users are sent to `/auth/signin`. After login, the home route redirects to `/feed`.
---
## Main UI routes
| Path | Page |
| --- | --- |
| `/auth/signin` | Sign in |
| `/auth/signup` | Sign up |
| `/auth/forgot-password` | Forgot password |
| `/auth/reset-password/:token` | Reset password |
| `/feed` | Home feed |
| `/explore` | Explore |
| `/search` | Search results |
| `/pin/create` | Create pin |
| `/pin/:id` | Pin detail |
| `/boards/:id` | Board |
| `/profile` | Profile |
| `/notifications` | Notifications |
| `/business` | Business directory |
| `/sponsored` | Sponsored pins |
---
## API overview
All authenticated calls send `Authorization: Bearer <token>`.
| Area | Base path |
| --- | --- |
| Auth | `/auth` (`POST /auth/login`, `POST /auth/logout`) |
| Users | `/users` (`POST /users/register`, …) |
| Password reset | `/password` |
| Profiles | `/profiles` |
| Pins | `/pins` |
| Pin media | `/pin-media` |
| Saves | `/saved` |
| Likes | `/likes` |
| Tags | `/tags` |
| Boards | `/boards` |
| Follow | `/follow` |
| Invitations | `/invitations` |
| Business | `/business` |
| Campaigns | `/campaigns` |
| Showcases | `/showcases` |
| Ads / sponsored | `/ads` |
Login example:
```http
POST /auth/login?usernameOrEmail=jane&password=secret
```
Successful login returns a JWT string. The frontend stores it in `localStorage` as `token` and attaches it on later requests.
---
## Scripts
**Frontend** (`pinterest_frontend`)
```bash
npm start          # development server
npm run build      # production build
npm test           # CRA test runner
```
**Backend** (`pinterest_backend`)
```bash
mvn spring-boot:run
mvn test
```
---
## Notes
- Keep MySQL credentials and `jwt.secret` out of git. `.env` files are gitignored.
- Change the default JWT secret before any shared or production use.
- Uploads are capped at 20 MB in `application.properties`.
- The frontend expects the API at `REACT_APP_API_URL` (defaults to `http://localhost:8080` if unset).
