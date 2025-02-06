package mk.ukim.finki.kiii.kiiiproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
    private String password;
    private LocalDate startDate;

    @Enumerated(EnumType.STRING)
    private StudentType type;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Course> courses;

    @Transient
    private int yearsOfStudying;

    public Student(String fullName, String email, String password, LocalDate startDate, StudentType type, List<Course> courses) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.startDate = startDate;
        this.type = type;
        this.courses = courses;
    }

    public int getYearsOfStudying() {
        if(this.startDate != null ) {
            return Period.between(this.startDate, LocalDate.now()).getYears();
        }
        return 0;
    }

}
