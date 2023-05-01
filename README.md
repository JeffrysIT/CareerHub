# Career RESTful API Microservice
The Vacancy RESTful API Microservice is a robust and reliable microservice that provides RESTful APIs to create and moderate job vacancies, store candidates' information and their Resumes (CVs).
Built on industry-best practices and with a modern tech stack, this microservice offers a seamless and efficient solution for managing job vacancies and candidate information. The APIs are designed to be simple to use, with easy-to-understand documentation and a user-friendly interface.

## Tech Stack
* Java 11
* Spring Boot 3.0.5
* Spring Data JPA
* Hibernate 6.1.7.Final
* MySQL Connector 8.0.32
* Map Struct 1.5.3.Final
### Dependencies
* jakarta.persistence-api 3.1.0
* h2 Database Engine
* Spring Boot Starter Test
## Build
* This project is built using Maven.


# API Description:
## Vacancies API:
### Get vacancies:
* Method: GET
* Endpoint: ```/api/v1/vacancies```
### Get vacancy by ID:
* Method: GET
* Endpoint: ```/api/v1/vacancies/{vacancyId}```
### Search vacancies:
* Method: GET
* Endpoint: ```/api/v1/vacancies/search```
### Delete vacancy:
* Method: DELETE
* Endpoint: ```/api/v1/vacancies/{vacancyId}```
### Get applications for a vacancy:
* Method: GET
* Endpoint: ```/api/v1/vacancies/{vacancyId}/applications```
### Create application for a candidate and vacancy:
* Method: POST
* Endpoint: ```/api/v1/vacancies/{vacancyId}/candidates/{candidateId}/applications```
### Create application for a candidate and vacancy:
* Method: PUT
* Endpoint: ```/api/v1/vacancies/{vacancyId}/candidates/{candidateId}/applications/{applicationId}```
### Get candidates from a vacancy:
* Method: GET
* Endpoint: ```/api/v1/vacancies/{vacancyId}/candidates```
### Get application from a candidate and vacancy:
* Method: GET
* Endpoint: ```/api/v1/vacancies/{vacancyId}/candidates/{candidateId}/applications/{applicationId}```
### Delete application from Vacancy:
* Method: DELETE
* Endpoint: ```/api/v1/{vacancyId}/candidates/{candidateId}/applications/{applicationId}```
### Change application status:
* Method: PATCH
* Endpoint: ```/api/v1/{vacancyId}/candidates/{candidateId}/applications/{applicationId}```

## Candidate API:

### Create Candidate
* Method: POST
* Endpoint: ```/api/v1/candidates```
* Required Body: CandidateCreateDTO
###  Get Candidate
* Method: GET
* Endpoint: ```/api/v1/candidates/{candidateId}```

### Delete Candidate
* Method: DELETE
* Endpoint: ```/api/v1/candidates/{candidateId}```

### Update Candidate
* Method: PUT
* Endpoint: ```/api/v1/candidates/{candidateId}```

* Required Body: CandidateUpdateDTO
### Get Candidate Applications
* Method: GET
* Endpoint: ```/api/v1/candidates/{candidateId}/applications```

### Get Candidate Application
* Method: GET
* Endpoint: ```/api/v1/candidates/{candidateId}/applications/{applicationId}```

### Update Candidate Application
* Method: PUT
* Endpoint: ```/api/v1/candidates/{candidateId}/applications/{applicationId}```

### Delete Candidate Application
* Method: DELETE
* Endpoint: ```/api/v1/candidates/{candidateId}/applications/{applicationId}```

### Get Candidate Vacancies
* Method: GET
* Endpoint: ```/api/v1/candidates/{candidateId}/vacancies```

### Get Candidate Resumes
* Method: GET
* Endpoint: ```/api/v1/candidates/{candidateId}/resumes```

### Upload Candidate Resume
* Method: POST
* Endpoint: ```/api/v1/candidates/{candidateId}/resumes```

* Required Body: MultipartFile
### Delete Candidate Resume
* Method: DELETE
* Endpoint: ```/api/v1/candidates/{candidateId}/resumes/{resumeId}```

### Download Candidate Resume
* Method: GET
* Endpoint: ```/api/v1/candidates/{candidateId}/resumes/{resumeId}/download```
### Note: The API endpoints are based on version 1 of the API and the base URL is not specified. The base URL should be added to each endpoint in order to make valid API requests.

## License

[MIT](https://choosealicense.com/licenses/mit/)