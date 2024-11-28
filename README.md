
# Library Management System

## Overview

The Library Management System is a RESTful API application built with Spring Boot to manage books and authors. It allows CRUD operations for books and authors, with relationships handled through a many-to-one association between books and authors.

## Features

- **Author Management**
  - Add, update, delete, and retrieve authors.
  - Search authors by last name.
- **Book Management**
  - Add, update, delete, and retrieve books.
  - Search books by author.
- RESTful endpoints for seamless integration.

## Technologies Used

- **Java**
- **Spring Boot**
- **Spring Data JPA**
- **MySQL Database**

## API Endpoints

### Authors

| Method | Endpoint                              | Description                    |
| ------ | ------------------------------------- | ------------------------------ |
| GET    | `/api/v1/authors`                     | Retrieve all authors.          |
| GET    | `/api/v1/authors/{id}`                | Retrieve an author by ID.      |
| GET    | `/api/v1/authors/lastname={lastname}` | Retrieve authors by last name. |
| POST   | `/api/v1/authors`                     | Add a new author.              |
| PUT    | `/api/v1/authors/{id}`                | Update an existing author.     |
| DELETE | `/api/v1/authors/{id}`                | Delete an author by ID.        |

#### Author Payload Example

**POST/PUT Request:**

```json
{
    "firstName": "Mark",
    "lastName": "Twain"
}
```

### Books

| Method | Endpoint                            | Description                    |
| ------ | ----------------------------------- | ------------------------------ |
| GET    | `/api/v1/books`                     | Retrieve all books.            |
| GET    | `/api/v1/books/{id}`                | Retrieve a book by ID.         |
| GET    | `/api/v1/books/author/{authorname}` | Retrieve books by author name. |
| POST   | `/api/v1/books`                     | Add a new book.                |
| PUT    | `/api/v1/books/{id}`                | Update an existing book.       |
| DELETE | `/api/v1/books/{id}`                | Delete a book by ID.           |

#### Book Payload Example

**POST/PUT Request:**

```json
{
    "name": "The Hunger Games",
    "author": 51,
    "release_date": "2008-09-14"
}
```

## Project Structure

```
src/main/java/com/library/server/
├── controller
│   ├── AuthorController.java
│   └── BookController.java
├── model
│   ├── Author.java
│   └── Book.java
├── repository
│   ├── AuthorRepo.java
│   └── BookRepo.java
├── service
│   ├── AuthorService.java
│   └── BookService.java
```

## Setup and Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/library-management-system.git
   ```
2. Navigate to the project directory:
   ```bash
   cd library-management-system
   ```
3. Configure the MySQL database:
   - Create a database named `librarydb`.
   - Update the `application.properties` file with your MySQL credentials:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/librarydb
     spring.datasource.username=<your-username>
     spring.datasource.password=<your-password>
     spring.jpa.hibernate.ddl-auto=update
     ```
4. Build the project:
   ```bash
   mvn clean install
   ```
5. Run the application:
   ```bash
   mvn spring-boot:run
   ```
6. Access the application at `http://localhost:8080`.

## Database

This application uses a MySQL database named `librarydb`. Ensure the database is set up and properly configured in the `application.properties` file.

### Default Configuration

- **Database Name:** `librarydb`
- **URL:** `jdbc:mysql://localhost:3306/librarydb`
- **Username:** `<your-username>`
- **Password:** `<your-password>`

## Future Enhancements

- Add pagination for large datasets.
- Implement advanced search and filtering.
- Add authentication and authorization.

## License

This project is licensed under the MIT License.
