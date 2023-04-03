CREATE TABLE user_details (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    resume_html TEXT
);

CREATE TABLE vacancies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    job_position VARCHAR(255) NOT NULL,
    description TEXT,
    viewed INT DEFAULT 0
);