package mk.ukim.finki.kiii.kiiiproject.repository;

import mk.ukim.finki.kiii.kiiiproject.model.Course;
import mk.ukim.finki.kiii.kiiiproject.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    //List<Student> findStudentsByStartDateBefore(LocalDate date);
    List<Student> findStudentsByStartDateBetween(LocalDate startDate, LocalDate endDate);
    List<Student> findStudentsByCoursesContaining(Course course);
    //List<Student> findStudentsByCoursesContainingAndStartDateBefore(Course course, LocalDate date);
    List<Student> findStudentsByCoursesContainingAndStartDateBetween(Course course, LocalDate startDate, LocalDate endDate);

    Student findByEmail(String username);
}
