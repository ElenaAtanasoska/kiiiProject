-- db/init/init.sql

-- Drop tables if they already exist
DROP TABLE IF EXISTS student_courses;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS courses;

-- Create the Course table
CREATE TABLE course
(
    id   SERIAL PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL
);

-- Insert initial data into the Skill table
INSERT INTO course (course_name)
VALUES ('KIII'),
       ('WBS'),
       ('VIS');

-- Create the Student table
CREATE TABLE student
(
    id              SERIAL PRIMARY KEY,
    full_name            VARCHAR(100) NOT NULL,
    email           VARCHAR(100) NOT NULL,
    password        VARCHAR(100) NOT NULL,
    type            VARCHAR(20)  NOT NULL,
    start_date      DATE         NOT NULL
);

-- Insert initial data into the Employee table
INSERT INTO student (id, email, start_date, full_name, password, type)
VALUES (1, 'student1@gmail.com', '2021-06-25', 'Student1', 'student123#', 'ROLE_ADMIN'),
       (2, 'elena@gmail.com', '2021-06-25', 'Elena Atanasoska', 'elena123#', 'ROLE_REGULAR');


-- Create the Student-Course join table
CREATE TABLE student_courses
(
    student_id INT NOT NULL,
    courses_id    INT NOT NULL,
    FOREIGN KEY (student_id) REFERENCES student (id),
    FOREIGN KEY (courses_id) REFERENCES course (id)
);

-- Insert initial data into the Employee-Skill join table
INSERT INTO student_courses (student_id, courses_id)
VALUES (1, 1),
       (2, 2);