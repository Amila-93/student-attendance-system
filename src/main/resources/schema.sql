CREATE DATABASE IF NOT EXISTS Student_attendance_database;
USE Student_attendance_database;

SELECT DATABASE();

CREATE TABLE IF NOT EXISTS Student(
    id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100)NOT NULL
);


CREATE TABLE IF NOT EXISTS Attendance(
    student_id VARCHAR(20) NOT NULL,
    id INT PRIMARY KEY AUTO_INCREMENT,
    status ENUM('IN','OUT') NOT NULL ,
    stamp DATETIME NOT NULL,
    CONSTRAINT fk_student_attendance_id FOREIGN KEY (student_id) REFERENCES Student(id)
);


CREATE TABLE IF NOT EXISTS Picture(
    student_id VARCHAR(20) PRIMARY KEY,
    picture MEDIUMBLOB NOT NULL,
    CONSTRAINT fk_student_picture_id FOREIGN KEY (student_id) REFERENCES Student(id)

);


CREATE TABLE IF NOT EXISTS User(
    user_name VARCHAR(50) PRIMARY KEY ,
    password VARCHAR(100) NOT NULL,
    full_name VARCHAR(100) NOT NULL
);
 #DROP TABLE User;
# DROP TABLE Picture;
# DROP TABLE Attendance;
# DROP TABLE Student;




