package jpa.systemstudent.service;

import jpa.systemstudent.dto.StudentDto;
import jpa.systemstudent.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface StudentService {
    List<StudentDto> getAllStudents();
    StudentDto getStudent(Long id);
    StudentDto saveStudent(StudentDto student);
    StudentDto UpdateStudent(Long id, StudentDto student);
    void deleteStudent(Long id);
    Page<Student> searchStudents(StudentDto request, int page);
}
