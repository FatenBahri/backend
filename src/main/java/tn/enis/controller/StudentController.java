package tn.enis.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import tn.enis.entities.Level;
import tn.enis.entities.Student;
import tn.enis.service.StudentServiceInterface;

@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentServiceInterface studentService;

    @GetMapping("/all")
    public List<Student> findStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public Optional<Student> getStudentById(@PathVariable Long id) {
        return studentService.getStudent(id);
    }

    @GetMapping("/search")
    public List<Student> searchByUsernameOrId(@RequestParam(required = false) String username,
                                              @RequestParam(required = false) Long id) {
        return studentService.search(username, id);
    }

    @GetMapping("/search/filter")
    public List<Student> filterByLevel(@RequestParam Level level) {
        return studentService.filter(level);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentById(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/add")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        try {
            Student createdStudent = studentService.createUpdateStudent(student);
            return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
        } catch (Exception e) {
        	System.err.println("Error creating student: " + e.getMessage());
        	return null;        }
    }
}
