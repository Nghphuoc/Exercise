package jpa.systemstudent.service.serviceImpl;

import jpa.systemstudent.dto.StudentDto;
import jpa.systemstudent.entity.Student;
import jpa.systemstudent.mapper.StudentMapper;
import jpa.systemstudent.repository.StudentRepository;
import jpa.systemstudent.service.StudentService;
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

    @Override
    public StudentDto getStudent(Long id) {
        // có thể sử dụng optional ở đây tránh lỗi NullPointer
        Student student = studentRepository.findById(id).orElseThrow(()->new RuntimeException("cannot found student with id: " + id));
        return StudentMapper.mapStudent(student);
    }

    @Override
    @Transactional
    public StudentDto saveStudent(StudentDto student) {
        studentRepository.save(StudentMapper.mapStudentDto(student));
        return student;
    }

    @Override
    @Transactional
    public StudentDto UpdateStudent(Long id, StudentDto student) {
        Student student1 = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
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
