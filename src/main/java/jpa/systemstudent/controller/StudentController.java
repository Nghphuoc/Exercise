package jpa.systemstudent.controller;

import jakarta.validation.Valid;
import jpa.systemstudent.dto.StudentDto;
import jpa.systemstudent.entity.Student;
import jpa.systemstudent.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/student")
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping()
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        List<StudentDto> studentDtos = studentService.getAllStudents();

        if(studentDtos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(studentDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        StudentDto studentDto = studentService.getStudent(id);

        if(studentDto == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(studentDto, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> createStudent(@RequestBody @Valid StudentDto studentDto) {
        //check trùng email
        List<StudentDto> studentDtos = studentService.getAllStudents();

        for(StudentDto studentDto2 : studentDtos){
            if(studentDto2.getEmail().equals(studentDto.getEmail())){
                return new ResponseEntity<>("Student with email= "+ studentDto.getEmail()+" already existed",HttpStatus.BAD_REQUEST);
            }
        }

        // check validate phone number
        if(!studentDto.getPhoneNumber().matches("^\\+84\\d{10}$")){
            return new ResponseEntity<>("Phone number is not valid",HttpStatus.BAD_REQUEST);
        }

        StudentDto studentDto1 = studentService.saveStudent(studentDto);
        return new ResponseEntity<>(studentDto1, HttpStatus.CREATED);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<Student>> searchStudents( @RequestBody StudentDto request, @RequestParam(defaultValue = "0") int page) {
        Page<Student> result = studentService.searchStudents(request, page);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody StudentDto studentDto) {
        // check phone
        if(!studentDto.getPhoneNumber().matches("^\\+84\\d{10}$")){
            return new ResponseEntity<>("Phone number is not valid",HttpStatus.BAD_REQUEST);
        }
        //check trùng email
        List<StudentDto> studentDtos = studentService.getAllStudents();
        for(StudentDto studentDto2 : studentDtos){
            // check mail trùng thì check tiếp
            if(studentDto2.getEmail().equals(studentDto.getEmail())){
                // check tiếp id nếu bằng thì cho update khác thì chắc chắn lad trùng với mail của người khácadd
                if(studentDto2.getStudentId().equals(id)){
                    StudentDto studentDto1 = studentService.UpdateStudent(id,studentDto);
                    return new ResponseEntity<>(studentDto1, HttpStatus.ACCEPTED);
                }
                return new ResponseEntity<>("Student with email= "+ studentDto.getEmail()+" already existed",
                        HttpStatus.BAD_REQUEST);
            }
        }
        // nếu không có mail nào trùng thì update
        StudentDto studentDto1 = studentService.UpdateStudent(id,studentDto);
        return new ResponseEntity<>(studentDto1, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>("Delete successfully!!!", HttpStatus.OK);
    }
}
