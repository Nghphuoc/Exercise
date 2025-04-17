package jpa.systemstudent.service.serviceImpl;

import jpa.systemstudent.dto.StudentDto;
import jpa.systemstudent.entity.Student;
import jpa.systemstudent.exception.StudentNotFoundException;
import jpa.systemstudent.mapper.StudentMapper;
import jpa.systemstudent.repository.StudentRepository;
import jpa.systemstudent.service.StudentService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(StudentMapper::mapStudent).collect(Collectors.toList());
    }

    @SneakyThrows
    @Override
    public StudentDto getStudent(Long id) {
        return studentRepository.findById(id)
                .map(StudentMapper::mapStudent)
                .orElseThrow(() -> new StudentNotFoundException(id));

    }

    @Override
    @Transactional
    public StudentDto saveStudent(StudentDto student) {
        studentRepository.save(StudentMapper.mapStudentDto(student));
        return student;
    }

    @SneakyThrows
    @Override
    @Transactional
    public StudentDto UpdateStudent(Long id, StudentDto student) {
        Student student1 = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
        student1.setStudentName(student.getStudentName());
        student1.setAge(student.getAge());
        student1.setAddress(student.getAddress());
        student1.setEmail(student.getEmail());
        student1.setBirthday(student.getBirthday());
        student1.setPhoneNumber(student.getPhoneNumber());
        studentRepository.save(student1);
        return StudentMapper.mapStudent(student1);
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Page<Student> searchStudents(StudentDto request, int page) {
        Pageable pageable = PageRequest.of(page, 5); // mỗi trang 5 item
        Specification<Student> spec = StudentSpecification.getStudentsByCriteria(request);
        return studentRepository.findAll(spec, pageable);
    }
}
