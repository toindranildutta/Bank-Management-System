# Banking Management System

## Overview

The Banking Management System is a comprehensive solution developed using Spring Boot that manages various banking operations. This project includes functionalities for account creation, credit, debit, and transfer of funds, as well as generating and sending account statements. It is secured with Spring Security and provides a REST API, with documentation available via Swagger.

## Features

- **Account Creation**: Create new bank accounts with ease.
- **Credit/Debit Transactions**: Perform credit and debit operations on accounts.
- **Fund Transfers**: Transfer funds between accounts.
- **Account Statements**: Generate account statements for specific date ranges.
- **Email Notifications**: Receive email alerts for debit and credit transactions.
- **Email Statement Delivery**: Send generated account statements via email.
- **API Documentation**: Access comprehensive API documentation through Swagger.
- **Secure API**: Ensure secure interactions with the application using Spring Security.

## Technologies Used

- **Spring Boot**: For building the backend application.
- **Spring Security**: For securing the REST API.
- **Swagger**: For API documentation.
- **Java Mail Sender**: For sending email notifications and statements.

## Installation

1. **Clone the repository**:
    ```bash
    git clone https://github.com/yourusername/banking-management-system.git
    cd banking-management-system
    ```

2. **Configure the application**:
    - Set up your database configuration in `application.properties`.
    - Configure email settings for Java Mail Sender in `application.properties`.

3. **Build and run the application**:
    ```bash
    ./mvnw clean install
    ./mvnw spring-boot:run
    ```

## API Documentation

The API documentation is available via Swagger. Once the application is running, navigate to:
```
http://localhost:8080/swagger-ui/index.html
```

## Security

This application uses Spring Security to protect the REST API endpoints. Ensure that the security configurations in `application.properties` are set up correctly to protect your application.

## Contributions

Contributions are welcome! Please open an issue or submit a pull request for any features, bug fixes, or improvements.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

## Contact

For any inquiries or support, please contact [toindranildutta@gmail.com](mailto:toindranildutta@gmail.com).

---

If you find this project useful, please give it a star on GitHub and consider giving credit if you use any part of it in your work. Thank you!
