package mk.ukim.finki.kiii.kiiiproject.service.impl;

import mk.ukim.finki.kiii.kiiiproject.model.Course;
import mk.ukim.finki.kiii.kiiiproject.model.exceptions.InvalidCourseIdException;
import mk.ukim.finki.kiii.kiiiproject.repository.CourseRepository;
import mk.ukim.finki.kiii.kiiiproject.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course findById(Long id) {
        return courseRepository.findById(id).orElseThrow(InvalidCourseIdException::new);
    }

    @Override
    public List<Course> listAll() {
        return courseRepository.findAll();
    }

    @Override
    public Course create(String courseName) {
        return courseRepository.save(new Course(courseName));
    }
}
