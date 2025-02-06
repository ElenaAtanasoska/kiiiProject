package mk.ukim.finki.kiii.kiiiproject.service;

import mk.ukim.finki.kiii.kiiiproject.model.Student;
import mk.ukim.finki.kiii.kiiiproject.model.StudentType;

import java.time.LocalDate;
import java.util.List;

public interface StudentService {
    List<Student> listAll();
    Student findById(Long id);
    Student create(String name, String email, String password, StudentType type, List<Long> courseId, LocalDate startDate);
    Student update(Long id, String name, String email, String password, StudentType type, List<Long> courseId, LocalDate startDate);
    Student delete(Long id);
    List<Student> filter(Long courseId, Integer yearsOfStudying);
}
