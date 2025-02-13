-- Drop tables if they already exist
DROP TABLE IF EXISTS student_courses;
DROP TABLE IF EXISTS student;
DROP TABLE IF EXISTS course;

-- Create the Course table
CREATE TABLE course
(
    id SERIAL PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL UNIQUE -- Add UNIQUE constraint to avoid duplicates
);

-- Insert initial data into the Course table
INSERT INTO course (course_name)
VALUES ('KIII'),
       ('WBS'),
       ('VIS')
    ON CONFLICT (course_name) DO NOTHING; -- Prevent duplicate entries

-- Create the Student table
CREATE TABLE student
(
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE, -- Add UNIQUE constraint for email
    password VARCHAR(100) NOT NULL,
    type VARCHAR(20) NOT NULL,
    start_date DATE NOT NULL
);

-- Insert initial data into the Student table
INSERT INTO student (email, start_date, full_name, password, type)
VALUES ('student1@gmail.com', '2021-06-25', 'Student1', 'student123#', 'ROLE_ADMIN'),
       ('elena@gmail.com', '2021-06-25', 'Elena Atanasoska', 'elena123#', 'ROLE_REGULAR')
    ON CONFLICT (id) DO NOTHING; -- Prevent duplicate entries

-- Create the Student-Course join table
CREATE TABLE student_courses
(
    student_id INT NOT NULL,
    courses_id INT NOT NULL,
    FOREIGN KEY (student_id) REFERENCES student (id),
    FOREIGN KEY (courses_id) REFERENCES course (id)
);

-- Insert initial data into the Student-Course join table
INSERT INTO student_courses (student_id, courses_id)
VALUES (1, 1),
       (2, 2)
    ON CONFLICT DO NOTHING; -- Prevent duplicate entries