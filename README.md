# Product Micro Service

 Overview

The Product Micro Service is a core component of the E-Commerce application. It handles all functionalities related to product management, including CRUD operations, inventory tracking, search, and filtering. Built with Spring Boot, this microservice leverages a modular design for easy integration and scalability.

---

 Features

-CRUD Operations: Create, read, update, and delete products.
-Bulk Upload: Support for bulk uploading products via CSV/JSON.
-Soft Delete: Archive products instead of permanent deletion.
-Product Attributes: Manage details like name, description, price, SKU, category, stock quantity, and image URLs.
-Product Variants: Support for variants such as size and color.
-Search & Filtering: Search by name, description, or SKU. Filter by category, price range, ratings, and availability. Sort by various criteria.
-Inventory Management: Real-time stock tracking, automatic stock deductions on order placements, and low-stock alerts.
-Categories & Tags: Create and manage product categories and assign tags for advanced filtering.
-Image Management: Upload and manage product images in cloud storage (e.g., AWS S3).

---

 Tech Stack

-Backend: Spring Boot, Spring Data JPA
-Database: PostgreSQL, Redis
-Event Streaming: Kafka or RabbitMQ
-Cloud Storage: AWS S3 or MinIO
-Containerization: Docker, Kubernetes

---

 Getting Started

# Prerequisites

- JDK 11 or higher
- Maven or Gradle
- PostgreSQL
- Docker & Docker Compose

# Installation

1.Clone the Repository:
    ```sh
    git clone https://github.com/cssuni/Ecom-Back-End-Microservices.git
    cd Ecom-Back-End-Microservices/Product Micro Service
    ```

2.Configure the Database:
    Update the `application.properties` file with your database settings:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/productdb
    spring.datasource.username=yourusername
    spring.datasource.password=yourpassword
    ```

3.Build the Application:
    ```sh
    mvn clean install
    ```

4.Run the Application:
    ```sh
    mvn spring-boot:run
    ```

5.Access the Application:
    The service will be accessible at `http://localhost:8080`.

---

 API Endpoints

-Get All Products: `GET /api/products`
-Get Product by ID: `GET /api/products/{id}`
-Create Product: `POST /api/products` (Admin only)
-Update Product: `PUT /api/products/{id}` (Admin only)
-Soft Delete Product: `DELETE /api/products/{id}` (Admin only)
-Bulk Upload Products: `POST /api/products/bulk`

---

 Contributing

Contributions are welcome! Please fork the repository and create a pull request. For major changes, please open an issue first to discuss what you would like to change.

---

 License

This project is licensed under the MIT License. See the LICENSE file for details.

---

 Acknowledgements

Thanks to the open-source community for providing the tools and frameworks that make this project possible.

