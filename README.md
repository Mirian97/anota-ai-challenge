# 👩🏻‍💻 AWS-Integrated Catalog Management

This is a system developed in **Java with Spring Boot** to manage products and categories, integrating with **MongoDB** as a database and utilizing **AWS services** (SNS and S3). It provides a RESTful API for CRUD operations on products and categories, as well as publishing notifications to **Amazon SNS** about data changes.

<img src="https://github-production-user-asset-6210df.s3.amazonaws.com/52219768/251830174-504ba448-f128-41db-ae86-18dc19c0dc9d.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20241123%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241123T180320Z&X-Amz-Expires=300&X-Amz-Signature=0c1ad6168974d8e5d3503d294967047e052ee75576de2e1ca5a161d3ce157682&X-Amz-SignedHeaders=host" alt="fluxo do projeto" />

## ⚙️ Features

### Products and Categories

- **Category CRUD**:

  - Create, update, delete, get and list categories.
  - Includes validations to ensure data integrity.
  - Integrates with SNS to notify about changes in categories.

- **Product CRUD**:
  - Full management of products associated with categories.
  - Validates product-to-category relationships before saving.
  - Publishes detailed messages to SNS after operations.

### AWS Integrations

- **Amazon SNS**:
  - Publishes notifications about changes in products and categories.
  - Configured with secure AWS credentials.
- **Amazon S3**:
  - Stores updated catalogs in JSON format for each user, organized by `ownerId`.

## 🛠️ Technologies

### Backend

- **Java**: The main programming language for this project.
- **Spring Boot**: Framework used to build RESTful APIs and manage dependencies.
  - **Spring Data MongoDB**: Abstraction layer for MongoDB access.
  - **Spring Context**: Manages beans and configurations.
  - **Lombok**: Reduces boilerplate code by generating getters, setters, and constructors.

### Database

- **MongoDB**: NoSQL database used to store product and category data.

### AWS

- **Amazon SNS**: Sends notifications related to the catalog.
- **Amazon S3**: Stores catalogs organized by user.

## ⚒️ Configuration and Execution

### Prerequisites

- Java 17+
- MongoDB running locally or in Docker (e.g., `mongodb://localhost:27017/product-catalog`)
- AWS credentials properly configured:
  - Set `AWS_ACCESS_KEY_ID` and `AWS_SECRET_ACCESS_KEY` in your `application.properties`.

### Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/Mirian97/aws-integrated-catalog-management.git
   cd aws-integrated-catalog-management
   ```
2. Configure the application.properties file with your AWS credentials:
   ```bash
   aws.accessKeyId=YOUR_AWS_ACCESS_KEY_ID
   aws.secretKey=YOUR_AWS_SECRET_ACCESS_KEY
   aws.region=us-east-2
   aws.sns.topic.catalog.arn=arn:aws:sns:us-east-2:EXAMPLE
   ```
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

### Key Endpoints

- #### Categories:

  - GET /api/category - Lists all categories.
  - POST /api/category - Creates a new category.
  - PUT /api/category/{id} - Updates a category.
  - DELETE /api/category/{id} - Deletes a category.

- #### Products:
  - GET /api/product - Lists all products.
  - POST /api/product - Creates a new product.
  - PUT /api/product/{id} - Updates a product.
  - DELETE /api/product/{id} - Deletes a product.

## 📜 License

This project is under [MIT](./LICENSE) license
