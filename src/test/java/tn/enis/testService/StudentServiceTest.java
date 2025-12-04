package tn.enis.testService;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import tn.enis.dao.StudentRepository;
import tn.enis.entities.Level;
import tn.enis.entities.Student;
import tn.enis.service.StudentServiceImplimentation;

@SpringBootTest
public class StudentServiceTest {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private StudentServiceImplimentation studentsevice;

    @BeforeEach
    public void cleanBase() {
        studentRepo.deleteAll();
        System.out.println("----- Database cleaned -----");
    }

    @Test
    public void testcreateUpdate() {
        Student s = Student.builder()
                .username("ahmed")
                .level(Level.Master)
                .build();
        Student x = studentsevice.createUpdateStudent(s);
        assertNotNull(x);
        assertEquals("ahmed", x.getUsername());
        assertNotNull(x.getId());
    }

    @Test
    public void testDelete() {
        Student s = Student.builder()
                .username("ahmed")
                .level(Level.Master)
                .build();

        Student s1 = studentsevice.createUpdateStudent(s);
        studentsevice.deleteStudent(s1.getId());
        Optional<Student> deleted = studentRepo.findById(s1.getId());
        assertTrue(deleted.isEmpty());
    }

    @Test
    public void testGetAll() {
        Student s = Student.builder()
                .username("ahmed")
                .level(Level.Master)
                .build();

        Student s1 = Student.builder()
                .username("kaneki")
                .level(Level.License)
                .build();

        studentsevice.createUpdateStudent(s);
        studentsevice.createUpdateStudent(s1);

        List<Student> l = studentsevice.getAllStudents();

        assertNotNull(l);
        assertEquals(2, l.size());

        Optional<Student> c = studentRepo.findById(5L);
        assertTrue(c.isPresent());
        assertEquals("ahmed", c.get().getUsername());
        //Optional<Student> c1 = studentRepo.findById(1L);
       // assertTrue(c.isPresent());
       // assertEquals("ahmed", c1.get().getUsername());
    }
    @Test
    public void testSearch() {
        Student s1 = Student.builder()
                .username("touka")
                .level(Level.Master)
                .build();

        Student s2 = Student.builder()
                .username("eren")
                .level(Level.License)
                .build();

        Student savedS1 = studentsevice.createUpdateStudent(s1);
        studentsevice.createUpdateStudent(s2);

        // Search by Username
        List<Student> resultsUsername = studentsevice.search("touka", null);
        assertNotNull(resultsUsername);
        assertEquals("touka", resultsUsername.get(0).getUsername());

        // Search by ID
        List<Student> resultsId = studentsevice.search(null, savedS1.getId());
        assertNotNull(resultsId);
        assertEquals(1, resultsId.size());
        assertEquals("eren", resultsId.get(0).getUsername());
        
        // Search by Username OR ID (Username match takes priority/or both return the same entity)
        List<Student> resultsBoth = studentsevice.search("touka", savedS1.getId());
        assertNotNull(resultsBoth);
    }

    @Test
    public void testFilter() {
        Student s1 = Student.builder()
                .username("alice")
                .level(Level.Master)
                .build();

        Student s2 = Student.builder()
                .username("charles")
                .level(Level.Master) 
                .build();

        Student s3 = Student.builder()
                .username("david")
                .level(Level.License) 
                .build();

        studentsevice.createUpdateStudent(s1);
        studentsevice.createUpdateStudent(s2);
        studentsevice.createUpdateStudent(s3);

        List<Student> masterStudents = studentsevice.filter(Level.Master);
        assertNotNull(masterStudents);
        assertEquals(2, masterStudents.size()); 

        List<Student> licenseStudents = studentsevice.filter(Level.License);
        assertNotNull(licenseStudents);
        assertEquals(1, licenseStudents.size());
        assertEquals("david", licenseStudents.get(0).getUsername());

        List<Student> primaryStudents = studentsevice.filter(Level.Primaire);
        assertNotNull(primaryStudents);
        assertTrue(primaryStudents.isEmpty());
    }
}
