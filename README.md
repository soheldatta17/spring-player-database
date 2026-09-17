# DemoProj - Spring Boot Backend

A comprehensive backend service built with Spring Boot, demonstrating the integration of various modern technologies like MongoDB, Redis caching, RabbitMQ message brokering, and JWT-based authentication. 

## Tech Stack

- **Java 21**
- **Spring Boot 3.x**
- **MongoDB** (Primary Database)
- **Redis Cloud** (Caching Layer)
- **RabbitMQ / CloudAMQP** (Asynchronous Messaging)
- **Spring Security + JWT** (Authentication)
- **Swagger UI / OpenAPI** (API Documentation)

## Features

- **JWT Authentication**: Secured endpoints using stateless JSON Web Tokens.
- **Player Management API**: CRUD operations for managing team players.
- **Performance Caching**: High-traffic endpoints (like fetching players) are cached using Redis. Cache eviction is automatically handled upon database mutations (POST/PUT/DELETE).
- **Asynchronous Events**: Creating a new player automatically publishes a `PlayerCreatedEvent` to a RabbitMQ exchange, which is subsequently processed by a background consumer.

## Prerequisites

Before running the application, make sure you have:
1. **Java 17+** (Java 21 recommended)
2. **Maven** installed
3. Accounts for managed services (or local instances running):
   - MongoDB Atlas (or local MongoDB)
   - Redis Cloud (or local Redis)
   - CloudAMQP (or local RabbitMQ)

## Local Setup & Configuration

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd spring-tutorial-course
   ```

2. **Environment Variables:**
   The application uses a `.env` file to manage secrets locally without hardcoding them into the codebase. 
   - Rename the provided `.env.example` file to `.env` in the root directory.
   - Fill in your actual credentials. For example:
     ```env
     MONGODB_URI=mongodb+srv://<username>:<password>@cluster0...
     JWT_SECRET=your_super_secret_jwt_key_here
     
     # Redis Cloud Configuration
     REDIS_HOST=redis-11395.c84...
     REDIS_PORT=11395
     REDIS_USERNAME=default
     REDIS_PASSWORD=your_redis_password
     
     # CloudAMQP Configuration
     RABBITMQ_HOST=capybara.lmq.cloudamqp.com
     RABBITMQ_PORT=5671
     RABBITMQ_USERNAME=your_rmq_username
     RABBITMQ_PASSWORD=your_rmq_password
     RABBITMQ_VHOST=your_rmq_vhost
     RABBITMQ_SSL=true
     ```

3. **Build the Application:**
   ```bash
   mvn clean package -DskipTests
   ```

4. **Run the Application:**
   ```bash
   mvn spring-boot:run
   ```
   The application will start on port `8080`.

## API Documentation & Testing

Once the application is running, you can test the endpoints interactively via Swagger UI:

1. Navigate to: `http://localhost:8080/swagger-ui/index.html`
2. **Authentication:** 
   - First, obtain a JWT token (e.g., by hitting your authentication endpoint with valid credentials).
   - Click the "Authorize" button (the lock icon) at the top of the Swagger UI.
   - Paste your token.
3. **Try it out:** Test the `GET /team/players` endpoint to see the Redis caching in action, and hit `POST /team/players` to trigger the RabbitMQ event publishing.

## Project Structure Highlights

- `config/` - Contains Spring configurations for Redis (`RedisConfig.java`), RabbitMQ (`RabbitMQConfig.java`), and Security/Swagger.
- `entity/` - MongoDB document models (e.g., `Player`). Note that cached entities implement `Serializable`.
- `event/` - Contains DTOs used for message payloads like `PlayerCreatedEvent`.
- `rabbitmq/` - Contains the `PlayerMessageProducer` and `PlayerMessageConsumer` for asynchronous processing.
- `service/` - Business logic and caching annotations (`@Cacheable`, `@CacheEvict`).

## Troubleshooting

- **Redis Connection Warnings:** If you see "Redis connection unavailable... Falling back to database" in the logs, ensure your `.env` contains the correct Redis Cloud URI and that you restarted the server after modifying the `.env` file.
- **RabbitMQ SSL Errors:** If you get `UnknownHostException` or SSL handshake errors, ensure you don't have double quotes `"` around your host variables in `.env`, and verify `RABBITMQ_PORT` is set to `5671` with `RABBITMQ_SSL=true` for CloudAMQP connections.
