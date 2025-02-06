package mk.ukim.finki.kiii.kiiiproject.repository;

import mk.ukim.finki.kiii.kiiiproject.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
