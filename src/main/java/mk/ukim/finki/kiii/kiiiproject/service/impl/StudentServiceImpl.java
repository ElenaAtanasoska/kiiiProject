package mk.ukim.finki.kiii.kiiiproject.service.impl;

import mk.ukim.finki.kiii.kiiiproject.model.Course;
import mk.ukim.finki.kiii.kiiiproject.model.Student;
import mk.ukim.finki.kiii.kiiiproject.model.StudentType;
import mk.ukim.finki.kiii.kiiiproject.model.exceptions.InvalidStudentIdException;
import mk.ukim.finki.kiii.kiiiproject.repository.StudentRepository;
import mk.ukim.finki.kiii.kiiiproject.service.CourseService;
import mk.ukim.finki.kiii.kiiiproject.service.StudentService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService, UserDetailsService {
    private final StudentRepository studentRepository;
    private final CourseService courseService;
    private final PasswordEncoder passwordEncoder;

    public StudentServiceImpl(StudentRepository studentRepository, CourseService courseService, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.courseService = courseService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Student> listAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id).orElseThrow(InvalidStudentIdException::new);

    }

    @Override
    public Student create(String name, String email, String password, StudentType type, List<Long> courseId, LocalDate startDate) {
        return studentRepository.save(new Student(name, email, password, startDate, type, courseId.stream().map(courseService::findById).collect(Collectors.toList())));

    }

    @Override
    public Student update(Long id, String name, String email, String password, StudentType type, List<Long> courseId, LocalDate startDate) {
        Student student = findById(id);
        student.setFullName(name);
        student.setEmail(email);
        student.setPassword(password);
        student.setType(type);
        student.setCourses(courseId.stream().map(courseService::findById).collect(Collectors.toList()));
        student.setStartDate(startDate);
        studentRepository.save(student);
        return student;
    }

    @Override
    public Student delete(Long id) {
        Student student = findById(id);
        studentRepository.deleteById(id);
        return student;
    }

    @Override
    public List<Student> filter(Long courseId, Integer yearsOfStudying) {
        if(courseId == null && yearsOfStudying == null){
            return studentRepository.findAll();
        } else if (yearsOfStudying == null){
            Course course = courseService.findById(courseId);
            return studentRepository.findStudentsByCoursesContaining(course);
        } else if (courseId == null){
            LocalDate endDate = LocalDate.now().minusYears(yearsOfStudying);
            LocalDate startDate = endDate.minusYears(1);
            //LocalDate studyingBefore = LocalDate.now().minusYears(yearsOfStudying);
            //return studentRepository.findStudentsByStartDateBefore(studyingBefore);
            return studentRepository.findStudentsByStartDateBetween(startDate, endDate);
        } else {
            Course course = courseService.findById(courseId);
            //LocalDate studyingBefore = LocalDate.now().minusYears(yearsOfStudying);
            //return studentRepository.findStudentsByCoursesContainingAndStartDateBefore(course, studyingBefore);
            LocalDate endDate = LocalDate.now().minusYears(yearsOfStudying);
            LocalDate startDate = endDate.minusYears(1); // Previous year
            return studentRepository.findStudentsByCoursesContainingAndStartDateBetween(course, startDate, endDate);
        }
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepository.findByEmail(username);
        return new User(
                student.getEmail(),
                passwordEncoder.encode(student.getPassword()),
                Collections.singleton(student.getType())
        );
    }
}
