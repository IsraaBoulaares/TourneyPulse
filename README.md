

# 🏆 TourneyPulse

# Microservices Sports Management Platform

[![Docker](https://img.shields.io/badge/Docker-Ready-blue)](https://www.docker.com/)
[![Spring](https://img.shields.io/badge/Spring-Boot-green)](https://spring.io/projects/spring-boot)
[![Keycloak](https://img.shields.io/badge/Keycloak-23.0.1-orange)](https://www.keycloak.org/)
[![Eureka](https://img.shields.io/badge/Eureka-Service%20Discovery-lightgrey)](https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-eureka-server.html)

A comprehensive microservices-based platform for sports management, including team management, tournament organization, stadium management, and reservation systems.

## Architecture Overview

This project uses a microservices architecture with the following components:

- **Service Discovery**: Eureka server for service registration and discovery
- **API Gateway**: Spring Cloud Gateway for routing and load balancing
- **Security**: Keycloak for authentication and authorization
- **Configuration**: Spring Cloud Config Server for centralized configuration
- **Microservices**: Individual services for different business domains
- **Databases**: MySQL for relational data and MongoDB for document storage

## Services

| Service | Port | Description |
|---------|------|-------------|
| Eureka | 8761 | Service discovery and registration |
| Keycloak | 8080 | Identity and access management |
| Gateway | 8093 | API Gateway for routing requests |
| Config Server | 8888 | Centralized configuration management |
| User Service | 8033 | User management and authentication |
| Stadium Management | 8082 | Stadium and venue management |
| Team Management | 8066 | Team and player management |
| Tournament Management | 8087 | Tournament organization and scheduling |
| Reservation Service | 8086 | Booking and reservation management |
| Reclamation Service | 3000 | Complaint and feedback management (Express.js) |

## Prerequisites

- [Docker](https://www.docker.com/products/docker-desktop) and Docker Compose
- [Git](https://git-scm.com/downloads)
- At least 8GB RAM for running all containers

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/yourusername/sports-management-platform.git
cd sports-management-platform
```

### Start the Services

```bash
docker-compose up -d
```

This will build and start all the services defined in the docker-compose file.

### Accessing Services

- **Eureka Dashboard**: http://localhost:8761
- **Keycloak Admin Console**: http://localhost:8080/admin (admin/admin)
- **API Gateway**: http://localhost:8093
- **Config Server**: http://localhost:8888

## Development Setup

### Project Structure

```
.
├── ConfigServer/      # Centralized configuration service
├── Gateway/           # API Gateway service
├── eurekea/           # Service discovery server
├── user/              # User management service
├── GestionEquipe/     # Team management service
├── tournoii/          # Tournament management service
├── SatduimManagment/  # Stadium management service
├── GestionReservation/# Reservation service
├── gestReclamation/   # Complaint management service (Node.js/Express)
├── keycloak/          # Keycloak configuration files
└── docker-compose.yml # Docker Compose configuration
```

### Building Individual Services

Each service can be built and run independently during development:

```bash
cd [service-directory]
# For Spring Boot services
./mvnw clean package
# For Node.js service
npm install
```

## Security

The platform uses Keycloak for security with:
- OAuth 2.0 / OpenID Connect protocols
- JWT token-based authentication
- Role-based access control
- Single Sign-On capabilities

A pre-configured realm is imported during startup from `keycloak/realm-export.json`.

## Network Configuration

All services communicate over a dedicated Docker network (`app-network`) with persistent storage volumes for:
- MySQL data
- MongoDB data
- Keycloak data

## Troubleshooting

### Service Health Checks

- MySQL and MongoDB have configured health checks to ensure availability
- Keycloak has a health check endpoint to confirm readiness

### Container Logs

```bash
# View logs for a specific service
docker logs [container_name]

# Follow logs in real-time
docker logs -f [container_name]
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
