package jpa.systemstudent.repository;

import jpa.systemstudent.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudentRepository extends JpaRepository<Student, Long> , JpaSpecificationExecutor<Student> {
    boolean existsByEmail(String email);
}
