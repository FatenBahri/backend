package tn.enis.service;

import java.util.List;
import java.util.Optional;

import tn.enis.entities.*;
public interface StudentServiceInterface {
   //crud 
	public List<Student> getAllStudents();
	public Optional getStudent(Long id);
	public Student createUpdateStudent(Student s);
	public void deleteStudent(Long id);
	public List<Student> search(String username,Long id);
	public List<Student> filter(Level level);

}
