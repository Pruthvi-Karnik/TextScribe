# TextScribe

### Distraction-Free Web Reader

TextScribe is a Spring Boot web application that extracts and cleans content from web pages and presents it in a simple, distraction-free reading format.

Instead of navigating through advertisements, navigation bars, pop-ups, and other unnecessary page elements, users can provide a URL and let TextScribe extract the relevant content for reading.

**Live Demo:** https://textscribe.onrender.com

---

## Features

* Extract readable content from web pages using a URL
* Remove unnecessary HTML elements and page clutter
* Calculate estimated reading time
* Store processed pages using Spring Data JPA
* Cache previously processed URLs to avoid unnecessary re-processing
* View previously processed content through reading history
* REST API for content-cleaning requests
* Containerized using Docker

---

## How It Works

```text
User enters URL
       │
       ▼
   Spring Boot
       │
       ▼
  URL Processing
       │
       ▼
   Jsoup Fetch
       │
       ▼
 HTML Parsing
       │
       ▼
Content Extraction
       │
       ▼
Remove Unwanted Elements
       │
       ▼
 Clean Text Content
       │
       ├──────────────► Calculate Reading Time
       │
       ▼
   Store Result
       │
       ▼
 Distraction-Free Reader
```

TextScribe first checks whether a URL has already been processed. If a cached result exists, it can be reused instead of processing the same page again.

---

## Architecture

TextScribe follows a layered Spring Boot architecture:

```text
┌──────────────────────────────┐
│         Thymeleaf UI         │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│        Controller Layer      │
│      UI Controller / API     │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│         Service Layer        │
│       Cleaning Service       │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│      Web Content Parsing     │
│             Jsoup            │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│       Repository Layer       │
│      Spring Data JPA         │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│          H2 Database         │
└──────────────────────────────┘
```

---

## Tech Stack

| Technology      | Purpose                             |
| --------------- | ----------------------------------- |
| Java            | Application development             |
| Spring Boot     | Backend framework                   |
| Spring MVC      | Web and request handling            |
| Spring Data JPA | Data persistence                    |
| Hibernate       | ORM                                 |
| H2              | Database                            |
| Jsoup           | HTML parsing and content extraction |
| Thymeleaf       | Server-side UI rendering            |
| Maven           | Dependency management and build     |
| Docker          | Containerization                    |

---

## REST API

TextScribe also exposes a REST endpoint for programmatic content extraction.

### Clean Web Content

```http
POST /api/v1/clean
```

Example request:

```json
{
  "url": "https://example.com/article"
}
```

The API processes the supplied URL and returns the extracted content along with relevant metadata.

---

## Project Structure

```text
src/
└── main/
    ├── java/
    │   └── com/
    │       └── TextScribe/
    │           └── demo/
    │               ├── controller/
    │               ├── dto/
    │               ├── entity/
    │               ├── repository/
    │               └── service/
    │
    └── resources/
        ├── static/
        ├── templates/
        └── application.properties
```

---

## Running Locally

### Prerequisites

* Java 17+
* Maven

### Clone the repository

```bash
git clone https://github.com/Pruthvi-Karnik/TextScribe.git
cd TextScribe
```

### Run the application

On Windows:

```bash
./mvnw.cmd spring-boot:run
```

On Linux/macOS:

```bash
./mvnw spring-boot:run
```

The application will start on:

```text
http://localhost:8080
```

---

## Docker

TextScribe also includes a Dockerfile for containerized deployment.

Build the image:

```bash
docker build -t textscribe .
```

Run the container:

```bash
docker run -p 8080:8080 textscribe
```

---

## Future Improvements

* Support for dynamically rendered web pages
* Improved article/content detection
* Persistent production database
* Export cleaned content to Markdown/PDF
* Improved error handling and validation
* Automated tests for content extraction
* Additional reading and content-management features

---

## License

This project is currently intended as a personal/student portfolio project.
