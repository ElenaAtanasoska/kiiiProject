package mk.ukim.finki.kiii.kiiiproject.web;

import mk.ukim.finki.kiii.kiiiproject.model.Student;
import mk.ukim.finki.kiii.kiiiproject.model.StudentType;
import mk.ukim.finki.kiii.kiiiproject.service.CourseService;
import mk.ukim.finki.kiii.kiiiproject.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class StudentController {
    private final StudentService studentService;
    private final CourseService courseService;

    public StudentController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping(value = {"/", "/students"})
    public String showList(Model model,
                           @RequestParam(required = false) Long courseId,
                           @RequestParam(required = false) Integer yearsOfStudying) {
        List<Student> students;
        if (courseId == null && yearsOfStudying == null) {
            students = this.studentService.listAll();

        } else {
            students = this.studentService.filter(courseId, yearsOfStudying);
        }
        model.addAttribute("students", students);
        model.addAttribute("courses", courseService.listAll());
        return "list";
    }

    @GetMapping("/students/add")
    public String showAdd(Model model) {
        model.addAttribute("courses", courseService.listAll());
        model.addAttribute("types", StudentType.values());
        return "form";
    }

    @GetMapping("/students/{id}/edit")
    public String showEdit(@PathVariable Long id, Model model) {
        model.addAttribute("students", studentService.findById(id));
        model.addAttribute("courses", courseService.listAll());
        model.addAttribute("types", StudentType.values());
        return "form";
    }

    @PostMapping("/students")
    public String create(@RequestParam String name,
                         @RequestParam String email,
                         @RequestParam String password,
                         @RequestParam StudentType type,
                         @RequestParam List<Long> courseId,
                         @RequestParam LocalDate startDate) {
        this.studentService.create(name, email, password, type, courseId, startDate);
        return "redirect:/students";

    }

    @PostMapping("/students/{id}")
    public String update(@PathVariable Long id,
                         @RequestParam String name,
                         @RequestParam String email,
                         @RequestParam String password,
                         @RequestParam StudentType type,
                         @RequestParam List<Long> courseId,
                         @RequestParam LocalDate startDate) {
        this.studentService.update(id, name, email, password, type, courseId, startDate);
        return "redirect:/students";

    }

    @PostMapping("/students/{id}/delete")
    public String delete(@PathVariable Long id) {
        this.studentService.delete(id);
        return "redirect:/students";
    }
}
