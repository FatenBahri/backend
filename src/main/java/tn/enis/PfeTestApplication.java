package tn.enis;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.AllArgsConstructor;
import tn.enis.dao.StudentRepository;
import tn.enis.entities.Level;
import tn.enis.entities.Student;

@SpringBootApplication
@AllArgsConstructor

public class PfeTestApplication implements CommandLineRunner {
	StudentRepository studentrep;
	public static void main(String[] args) {
		SpringApplication.run(PfeTestApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		Student s1 = Student.builder().username("faten").level(Level.Bac)
				.build();
		studentrep.save(s1);
		Student s2 = Student.builder()
                .username("fathi")
                .level(Level.Secondaire)
                .build();
        studentrep.save(s2);
        System.out.println(studentrep.findById(2L));
       // studentrep.delete(s2);
       

	}
	
}
