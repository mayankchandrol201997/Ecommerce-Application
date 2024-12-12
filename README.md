# Ecommerce Application - Microservices Backend

This project is a microservice-based backend for an eCommerce platform developed using **Spring Boot**. The application demonstrates an end-to-end eCommerce system with various core functionalities, including product management, order processing, user authentication, and payment integration. The system follows a microservices architecture and uses various modern tools and technologies to build a robust and scalable solution.

## Key Features:
- **Microservices Architecture**: 
  - A modular backend system with separate services for handling different responsibilities.
  - Includes services like Product Service, Order Service, User Service, and Payment Service.
  
- **Spring Boot**: 
  - Used for building each microservice, ensuring ease of development and scalability.

- **OAuth 2 Authentication**:
  - User service acts as an OAuth 2.0 authorization server for secure authentication and authorization.
  
- **Payment Gateway Integration**:
  - Integrated with popular payment gateways such as **RazorPay** and **Stripe** for handling transactions.
  
- **Event-Driven Architecture**:
  - Utilized **Apache Kafka** for asynchronous messaging and event-driven communication between services.

- **Spring Cloud API Gateway**:
  - Used for routing and load balancing across microservices, ensuring high availability and performance.

- **Database**:
  - The application uses **MySQL** for storing and managing data across the different microservices.
  
- **Scalability & Fault Tolerance**:
  - Integrated **Spring Cloud Load Balancer** for efficient load distribution across microservices.

## Technologies Used:
- **Spring Boot**: Main framework for developing the microservices.
- **Spring OAuth 2**: For implementing OAuth 2.0-based user authentication.
- **Apache Kafka**: For event-driven communication between microservices.
- **Spring Cloud**: For API Gateway, Load Balancer, and other cloud features.
- **MySQL**: Relational database for data storage.
- **RazorPay & Stripe**: Payment gateways for handling transactions.
  
## Project Structure:
1. **Product Service**: 
   - Manages product and category details.
  
2. **Order Service**: 
   - Handles user orders, including creating, updating, and processing orders.
  
3. **User Service**:
   - Acts as an OAuth 2.0 authorization server for managing user authentication and authorization.
  
4. **Payment Service**: 
   - Handles all payment transactions and integrates with RazorPay and Stripe for payment processing.
  
5. **API Gateway**: 
   - A Spring Cloud API Gateway that routes and load-balances requests to appropriate microservices.
  
6. **Kafka**:
   - Implements event-driven architecture, allowing reliable communication between services via Apache Kafka.
