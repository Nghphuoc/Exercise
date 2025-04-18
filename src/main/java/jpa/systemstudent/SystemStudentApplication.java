package jpa.systemstudent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class SystemStudentApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemStudentApplication.class, args);
    }
}
