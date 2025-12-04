package tn.enis.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tn.enis.entities.Level;
import tn.enis.entities.Student;
import tn.enis.service.StudentServiceInterface;

@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200") // ⬅️ CORS ESSENTIEL
@RestController
@RequestMapping("/students")
public class StudentController {
	StudentServiceInterface studentIn;
   @RequestMapping(value="/all", method=RequestMethod.GET)
   public List<Student> findStudents(){
	   return studentIn.getAllStudents();   }
   @GetMapping("students/{id}")
   public Optional getStudentById(@PathVariable Long id) { 
    return studentIn.getStudent(id); }
   @GetMapping(value="/students/search")
	public List<Student> searchByUsernameOrId(@RequestParam(required = false) String username,@RequestParam(required = false) Long id)
	{
	return studentIn.search(username,id);
	}
   @GetMapping(value="/students/search/filter")
	public List<Student> filterByLevel(@RequestParam Level level)
	{
	return studentIn.filter(level);
	}
   @DeleteMapping("/{id}")
   public void Delete(@PathVariable Long id) {
	   studentIn.deleteStudent(id);
   }
}
