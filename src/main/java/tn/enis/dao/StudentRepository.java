package tn.enis.dao;
import tn.enis.entities.*;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
public interface StudentRepository extends JpaRepository<Student,Long> {
 List<Student>findByUsernameOrId(String username,Long id); 
 List<Student>findByLevel(Level niveau);
 Optional findById(Long id);
 
}