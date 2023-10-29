# Data Reconciliation Service

## Introduction

The Data Reconciliation Service is a solution designed to efficiently reconcile data between an RDBMS (PostgreSQL) and Elasticsearch. It provides advanced capabilities for managing the reconciliation process, ensuring data consistency and optimization. This service is developed using Spring Boot, offering a simplified and highly configurable approach.

## Architecture Overview

The Data Reconciliation Service consists of the following key components:

- Data Retrieval
- Data Comparison
- Data Update

The service facilitates incremental updates, enabling the comparison of datasets between the RDBMS and Elasticsearch, and updating missing values. The use of Spring Boot ensures seamless development and deployment.

## Features

- Query the latest data from the RDBMS (PostgreSQL) and Elasticsearch.
- Compare datasets based on data IDs to identify missing values or discrepancies.
- Update missing values in Elasticsearch to ensure data consistency and integrity.
- Perform incremental updates for optimized performance and efficiency.
- Periodically monitor and reconcile data between the RDBMS and Elasticsearch.

## Getting Started

### Prerequisites

To set up and run the Data Reconciliation Service, ensure you have the following prerequisites installed:

- Spring Boot version 3.1.1
- Maven with Packaging set to Jar
- Java Development Kit (JDK) 8 or higher
- PostgreSQL vesrion 7.2
- Elasticsearch version 7.17.10

### Installation

1. Clone the repository:

   ```shell
   git clone https://github.com/ericmaniraguha/data_reconciliation_project.git

2. Navigate to the project directory
   `cd data-reconciliation-service`
3. Build the project using Maven `mvn clean install`
4. Configure the database and Elasticsearch connection details in the `application.properties` file.
5. Run the application: `java -jar data-reconciliation-service.jar`

Access the Data Reconciliation Service at `http://localhost:8080`.

## Testing Instructions

To ensure the correctness and reliability of our application, the following tests should be performed:

1. **Fetch All Data from RDBMS**:
   - Endpoint: [http://localhost:8080/rdbms/all-data](http://localhost:8080/rdbms/all-data)
   - Expected Behavior: Verify that the application can successfully retrieve all data from the RDBMS (Relational Database Management System) and return the appropriate response.

2. **Fetch All Data from Elasticsearch**:
   - Endpoint: [http://localhost:8080/elasticsearch](http://localhost:8080/elasticsearch)
   - Expected Behavior: Verify that the application can successfully fetch all data from Elasticsearch and return the expected response.

3. **Fetch Most Recent Data from RDBMS**:
   - Endpoint: [http://localhost:8080/rdbms/most-recent](http://localhost:8080/rdbms/most-recent)
   - Expected Behavior: Ensure that the application retrieves the most recent data from the RDBMS and returns the appropriate response.

4. **Fetch Most Recent Data from Elasticsearch**:
   - Endpoint: [http://localhost:8080/elasticsearch](http://localhost:8080/elasticsearch)
   - Expected Behavior: Confirm that the application can fetch the most recent data from Elasticsearch and returns the expected response.

## Contribution Guidelines
We welcome contributions from the community to enhance the Data Reconciliation Service. To contribute, please follow these guidelines:

- Fork the repository and create a new branch for your contribution.
- Make your changes and ensure they follow the coding standards.
- Write unit tests for new features or modifications.
- Submit a pull request with a detailed description of your changes.

##  Application Properties configurations

To run this application, you need to configure the following properties in the `application.properties file`:

Path: /src/main/resources/application.properties

- `spring.data.elasticsearch.cluster-nodes` - The IP address and port of the Elasticsearch cluster.
- `spring.data.elasticsearch.cluster-name` - The name of the Elasticsearch cluster.
- `spring.datasource.url` - The JDBC URL of the RDBMS.
- `spring.datasource.username` - The username for the RDBMS.
- `spring.datasource.password` - The password for the RDBMS.
- `spring.sql.init.mode` - The mode for initializing the database.

For example, the following configuration would connect to an Elasticsearch cluster at `localhost:9200` and an RDBMS at `localhost:5432/mydatabase`:

- `spring.data.elasticsearch.cluster-nodes=localhost:port`
- `spring.data.elasticsearch.cluster-name=mycluster`
- `spring.datasource.url=jdbc:postgresql://localhost:port/mydatabase`
- `spring.datasource.username=myusername`
- `spring.datasource.password=mypassword`
- `spring.sql.init.mode=always`

Once you have configured the application.properties file, you can run the application by issuing the following command:

mvn spring-boot:run

## License

The Data Reconciliation Service is released under the [MIT License](https://opensource.org/licenses/MIT).

