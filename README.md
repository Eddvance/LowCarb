# 🌱 LowCarb

**Carbon footprint calculator** — A microservices-based application that calculates energy consumption costs and carbon impact.

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green?logo=springboot)
![Docker](https://img.shields.io/badge/Docker-Ready-blue?logo=docker)
![License](https://img.shields.io/badge/License-MIT-yellow)

---

## 📋 Overview

LowCarb is a full-stack application that helps users compare the cost of green energy versus carbon-based energy. Enter your kilowatt usage, and the system calculates your consumption cost using rates from both energy sources — helping you make more environmentally conscious choices.

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────────────────────┐
│                              FRONTEND                                    │
│                         (LowCarbFront :3002)                            │
└───────────────────────────────┬─────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                         CORE APPLICATION                                 │
│                          LowCarb (:8080)                                │
│                                                                          │
│  • Receives kWh input from frontend                                     │
│  • Orchestrates calls to energy services                                │
│  • Compares green vs carbon energy costs                                │
│  • Stores consumption history (PostgreSQL)                              │
└───────────┬─────────────────────────────────┬───────────────────────────┘
            │                                 │
            ▼                                 ▼
┌───────────────────────┐       ┌───────────────────────┐
│     CoalFired         │       │    LowCarbPower       │
│      (:3000)          │       │       (:8081)         │
│                       │       │                       │
│  Carbon energy rate   │       │   Green energy rate   │
│  (real NZ data)       │       │   (mocked)            │
└───────────────────────┘       └───────────────────────┘
            │                                 │
            └────────────┬────────────────────┘
                         ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                      SERVICE DISCOVERY                                   │
│                  DiscoveryLowCarb (:8761)                               │
│                     (Eureka Server)                                      │
└─────────────────────────────────────────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                        MONITORING                                        │
│            Monitoring (:8082) → Prometheus (:9090) → Grafana (:3001)    │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## 🧩 Microservices

| Service | Port | Description |
|---------|------|-------------|
| **LowCarb** | 8080 | Core application — orchestrates energy calculations |
| **LowCarbPower** | 8081 | Green energy rate API (mocked data) |
| **CoalFired** | 3000 | Carbon energy rate API (real NZ rates) |
| **DiscoveryLowCarb** | 8761 | Eureka service registry |
| **Monitoring** | 8082 | Exposes metrics via Micrometer |
| **LowCarbFront** | 3002 | Static frontend (HTML/CSS) |

> ⚠️ **Note:** LowCarbPower returns mock data for demo purposes. CoalFired fetches real carbon energy rates from New Zealand.

---

## 🛠️ Tech Stack

**Backend**
- Java 17
- Spring Boot 3.x
- Spring WebFlux (Reactive)
- Spring Cloud Netflix Eureka
- PostgreSQL
- OpenAPI / Swagger (API-first design)

**Frontend**
- HTML / CSS / JavaScript

**DevOps & Monitoring**
- Docker & Docker Compose
- Prometheus
- Grafana
- Micrometer

---

## 🚀 Getting Started

### Prerequisites

- Docker & Docker Compose
- Java 17+ (for local development)
- Maven

### Run with Docker

```bash
# Clone the repository
git clone https://github.com/Eddvance/LowCarb.git
cd LowCarb

# Start all services
docker-compose up -d

# Check status
docker-compose ps
```

### Access the application

| Service | URL |
|---------|-----|
| Frontend | http://localhost:3002 |
| API | http://localhost:8080 |
| Eureka Dashboard | http://localhost:8761 |
| Prometheus | http://localhost:9090 |
| Grafana | http://localhost:3001 |

> Grafana credentials: `admin` / `admin`

---

## 📡 API Usage

### Calculate carbon cost

```bash
curl -X POST http://localhost:8080/low-carb \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "ratingRequest": 1000
  }'
```

### Health check

```bash
curl http://localhost:8080/actuator/health
```

---

## 📊 Monitoring

The application exposes metrics via **Micrometer** and collects them with **Prometheus**.

Pre-configured Grafana dashboards include:
- HTTP request rates
- Response times
- JVM metrics
- Service health status

Access metrics directly:
```bash
curl http://localhost:8080/actuator/prometheus
```

---

## 🗂️ Project Structure

```
LowCarb/
├── LowCarb/              # Core application
├── LowCarbPower/         # Green energy rate service (mocked)
├── CoalFired/            # Carbon rate service (NZ data)
├── DiscoveryLowCarb/     # Eureka server
├── Monitoring/           # Metrics service
├── lowcarbfront/         # Static frontend (HTML/CSS)
├── docker-compose.yml
├── prometheus.yml
└── README.md
```

---

## 🧪 Running Tests

```bash
# Run tests for all services
./mvnw test

# Run tests for a specific service
cd LowCarb && ./mvnw test
```

---

## 📝 License

This project is licensed under the MIT License.

---

## 👤 Author

**Edouard Leroy**  
Backend Developer | Java & Spring Boot  
[GitHub](https://github.com/Eddvance)
