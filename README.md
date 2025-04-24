# CareCompanion – Chronic Disease Management & Remote Monitoring Platform

Welcome to the CareCompanion repository for the IJSE-GDSE70 2nd Semester Final Project.

---

## 🚀 Screenshots

**Login / Role Selection**  
![Login Screen1](/screenshots/login1.png)
![Login Screen2](/screenshots/login2.png)
![Login Screen3](/screenshots/login3.png)

**Patient Dashboard**
![Patient Dashboard](/screenshots/patientDashboard.png)

**Provider Dashboard**
![Provider Dashboard](/screenshots/providerDashboard.png)

**Patient Appointments**
![Patient Appointments](/screenshots/patientAppointments.png)

**Provider Appointments**
![Provider Appointments](/screenshots/providerAppoinment.png)

**Patient HealthData**
![Patient HealthData](/screenshots/patientHealthData.png)

**Provider HealthData**
![Provider HealthData](/screenshots/providerHealthData.png)

**Profile Management**
![Profile Management](/screenshots/patientProfileManagement.png)

**Registration**
![Registration](/screenshots/providerRegistration.png)

**Patient Symptoms**
![Patient Symptoms](/screenshots/patientsymptom.png)

**Provider's Patients**
![Provider's Patients](/screenshots/providerpatientManagement.png)

## 🛠️ Technologies Used

### Frontend
- HTML
- CSS
- JavaScript & jQuery
- Bootstrap 5
- [FullCalendar](https://fullcalendar.io/)
- [Chart.js](https://www.chartjs.org/)
- [Flatpickr](https://flatpickr.js.org/)

### Backend
- Spring Boot (Spring MVC, Spring Data JPA, Spring Security)
- Java 17
- JWT for authentication & authorization

### Database
- MySQL

---

## ✨ Features

- **Role‐based Access**
    - Patients and Providers each have their own views and permissions.
- **Patient Health Tracking**
    - Log vital signs (BP, glucose, heart rate, weight).
    - Symptom diary with severity markers.
- **Interactive Dashboards**
    - Time-series charts with threshold overlays.
    - Last-6-entry quick-view bars.
- **Medication & Appointment Management**
    - Schedule medications with reminders.
    - Request, confirm, reschedule, and cancel appointments.
- **Alerts & Notifications**
    - Automatic alerts on out-of-range readings.
    - In-app toasts for success / error messages.
- **Secure REST API**
    - JWT-protected endpoints.
    - CORS and CSRF properly configured.

---

## ⚙️ Getting Started

### Prerequisites

- Java 17 JDK
- Maven 3.8+
- MySQL 8+
- Node.js & npm (for local frontend development, optional)

### Installation & Run

1. **Clone the repository**
   ```bash
   https://github.com/MaheshaDinu/CareCompanion.git
   cd carecompanion

2. **Database Setup**
   Database Setup

Create a MySQL schema, e.g. care_companion.

In src/main/resources/application.properties, set:

properties
Copy
Edit
spring.datasource.url=jdbc:mysql://localhost:3306/care_companion
spring.datasource.username=YOUR_DB_USER
spring.datasource.password=YOUR_DB_PASS
Build & Run Backend

bash
Copy
Edit
mvn clean install
mvn spring-boot:run
Launch Frontend

If served via Spring Boot, open http://localhost:8080/ in your browser.

**Or in the frontend/ folder run:**

bash
Copy
Edit
npx live-server
then navigate to the served URL.

**Access the App**

Backend API: http://localhost:8080

Frontend UI: http://localhost:8080 (or your live-server address)

**📺 Demo**

Watch a quick walkthrough on YouTube:
https://youtu.be/MqP785k6C4k