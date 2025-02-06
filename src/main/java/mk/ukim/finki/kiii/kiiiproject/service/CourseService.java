package mk.ukim.finki.kiii.kiiiproject.service;

import mk.ukim.finki.kiii.kiiiproject.model.Course;

import java.util.List;

public interface CourseService {
    Course findById(Long id);
    List<Course> listAll();
    Course create(String courseName);
}
