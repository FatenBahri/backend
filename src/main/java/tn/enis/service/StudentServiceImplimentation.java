package tn.enis.service;

import tn.enis.dao.StudentRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tn.enis.entities.Level;
import tn.enis.entities.Student;
@Service
@AllArgsConstructor

public class StudentServiceImplimentation implements StudentServiceInterface {
	StudentRepository student;
	@Override
	public List<Student> getAllStudents(){
		return student.findAll();}
	public Optional getStudent(Long id) {
		return student.findById(id);}
	public Student createUpdateStudent(Student s) {
		return student.save(s);}
	public void deleteStudent(Long id){
		     student.deleteById(id);;
			}
	public List<Student> search(String username,Long id){
		return student.findByUsernameOrId(username,id);}
	public List<Student> filter(Level level){
		return student.findByLevel(level);}
}
