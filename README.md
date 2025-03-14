# Job Board Backend

This is the **backend** service of the **Job Board** application. It is built using **Spring Boot** and **H2 Database (file system)** to store job listings. The backend fetches job postings via web scraping, saves them to the database, and serves them through a **REST API**.

##  **Technologies Used**
- **Java 21** - Main programming language
- **Spring Boot** - Backend framework
- **Spring Data JPA** - ORM for database interactions
- **H2 Database (File System)** - Lightweight in-memory database
- **Jsoup** - HTML parser for web scraping
- **Railway** - Cloud deployment platform
- **Maven** - Build and dependency management tool

---

## **Project Structure**
/src/main/java/org/jobboard/
├── controller/ # Handles HTTP requests 
├── model/ # Defines data structures 
├── repository/ # Interfaces for DB operations 
├── service/ # Business logic & job scraping 
└── JobApplication.java # Main Spring Boot entry point


## **Endpoints**
### 1. **Fetch Jobs (Paginated)**
- **`GET /api/jobs?title={title}&page={page}&size={size}`**
- Retrieves stored job listings with infinite scrolling.

### 2. **Scrape Jobs Manually**
- **`GET /api/jobs/scrape?title={title}`**
- Triggers web scraping if a job title doesn’t exist.
---

## 📦 **Setup and Run Locally**
### **Prerequisites**
- Install **Java 21**
- Install **Maven**

### **Run Locally**
1. **Clone the repository**
   ```sh
   git clone https://github.com/your-username/job-board-backend.git
   cd job-board-backend




