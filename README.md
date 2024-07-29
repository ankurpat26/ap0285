# tool-rental-application

## Overview

This project is a **Tool Rental Service** application built with Spring Boot. It provides features for managing tools and tool types, as well as a checkout process that includes rental charge calculations.

## Prerequisites

- **Java 17**: Ensure Java 17 is installed on your machine. You can download it from the [official Oracle website](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) or use a package manager like SDKMAN.
- **Maven**: Apache Maven should be installed. You can download it from the [official Maven website](https://maven.apache.org/download.cgi) or use a package manager like Homebrew (on macOS) or apt (on Ubuntu).

## Technologies

- **Java 17**
- **Spring Boot 3.3.2**
- **Maven**

## Steps to Build and Run the Application from Console

### 1. Clone the Repository (if not already done)

If you haven't cloned the repository yet, you can do so using the following command:

```sh
git clone https://github.com/ankurpat26/ap0285.git
cd tool-rental
```
### 2. Navigate to the Project Directory

Ensure you are in the project directory:
```sh
cd tool-rental
```

### 3. Build the Application

Use Maven to build the application. This will compile the code, run the tests, and package the application:

```sh
mvn clean install
```

### 4. Run Integration Tests
To run the integration and Unit tests, use the following Maven command:
```sh
mvn test
```

### 5. Run the Application
After a successful build, you can run the application using the following command:
```sh
mvn spring-boot:run
```



## Usage

### API Endpoints


- **Checkout Process**
  - **URL**: `/api/checkout`
  - **Method**: `POST`
  - **Description**: Perform checkout and calculate rental charges.
  - **Example Request:**
    ```sh
    curl --location 'http://localhost:8080/api/checkout' \
    --header 'Content-Type: application/json' \
    --data '{
      "toolCode": "LADW",
      "rentalDayCount": 3,
      "discountPercent": "10%",
      "checkoutDate": "7/2/20"
    }'
    ```
  - **Example Response:**
    ```json
    {
        "toolCode": "LADW",
        "toolType": "Ladder",
        "toolBrand": "Werner",
        "rentalDays": 3,
        "checkoutDate": "07/02/20",
        "dueDate": "07/04/20",
        "dailyRentalCharge": "$1.99",
        "chargeDays": 2,
        "preDiscountCharge": "$3.98",
        "discountPercent": "10%",
        "discountAmount": "$0.40",
        "finalCharge": "$3.58"
    }
    ```

- **ToolType Endpoints**

  - **Create a ToolType**
    - **URL**: `/api/toolTypes`
    - **Method**: `POST`
    - **Description**: Creates a new tool type.

  - **Get All ToolTypes**
    - **URL**: `/api/toolTypes`
    - **Method**: `GET`
    - **Description**: Retrieves a list of all tool types.

  - **Get ToolType by ID**
    - **URL**: `/api/toolTypes/{toolType}`
    - **Method**: `GET`
    - **Path Variable**: `toolType` (String)
    - **Description**: Retrieves a specific tool type by its ID.

  - **Delete ToolType**
    - **URL**: `/api/toolTypes/{toolType}`
    - **Method**: `DELETE`
    - **Path Variable**: `toolType` (String)
    - **Description**: Deletes a specific tool type by its ID.

- **Tool Endpoints**

  - **Create a Tool**
    - **URL**: `/api/tools`
    - **Method**: `POST`
    - **Description**: Creates a new tool.

  - **Get All Tools**
    - **URL**: `/api/tools`
    - **Method**: `GET`
    - **Description**: Retrieves a list of all tools.

  - **Get Tool by ID**
    - **URL**: `/api/tools/{toolCode}`
    - **Method**: `GET`
    - **Path Variable**: `toolCode` (String)
    - **Description**: Retrieves a specific tool by its ID.

  - **Delete Tool**
    - **URL**: `/api/tools/{toolCode}`
    - **Method**: `DELETE`
    - **Path Variable**: `toolCode` (String)
    - **Description**: Deletes a specific tool by its ID.

## Key Points
- ### Configuration

  **Application Properties**: Located in `src/main/resources/application.properties`. Update the database connection details as needed.

- ### Data Initialization

  - **Initial Data**: The application initializes data on startup using the `data.sql` file located in `src/main/resources`. Modify this file to change the initial data.
  - **Test Data**: Test cases also initialize data as needed to ensure consistent test results.

- ### Exception Handling
  Global exception handling is provided to ensure consistent error responses. This is implemented in the `GlobalExceptionHandler` class, which handles various exceptions and returns appropriate HTTP status codes and error messages. Custom exceptions like `RequestPayloadException` are handled here. The response body includes an error code, error message, and status code.

- ### Serialization/Deserialization

  Custom serializers and deserializers are provided for special formatting needs.
  These classes ensure that data is correctly formatted during API responses and requests.
  - **CurrencySerializer**: Serializes currency values. e.g.,$9,999.99, $99.99
  - **PercentSerializer**: Serializes percentage values. e.g., 99%, 10%, 100%
  - **DateSerializer**: Serializes dates in format of mm/dd/yy. e.g., 07/08/2020, 8/3/2024 
  - **DateDeserializer**: Deserializes dates to LocalDate.class Java object. e.g., 07/08/2020, 8/3/2024
  - **PercentDeserializer**: Deserializes percentage values to int. e.g., 99%, 10%, 100%
  
  
- ### Holiday Utility

  The `HolidaysUtil` class provides utility methods to get holiday dates based on the year. This is useful for calculating rental periods that exclude holidays. Below holidays has been considered.
  - **Independence Day, July 4th**: If falls on weekend, it is observed on the closest weekday (if Sat,
  then Friday before, if Sunday, then Monday after)
  - **Labor Day**: First Monday in September


- ### Validation and Request Interception

  Interception of request bodies and validation of data formats and values are performed to ensure data integrity. If there is an issue with the request body, a 400 error is thrown with specific error scenarios.

- ### Testing

  - **Integration Tests**:
    - Integration tests for the `/checkout` endpoint are included, covering all given test case scenarios.
  - **Unit Tests**:
      - Unit tests for all service classes are included, using JUnit.

  - **Run Tests**:
    ```sh
    ./mvnw test 
    ```