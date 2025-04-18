package jpa.systemstudent;

import jakarta.persistence.EntityManager;
import jpa.systemstudent.controller.StudentController;
import jpa.systemstudent.dto.StudentDto;
import jpa.systemstudent.service.StudentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {

    @InjectMocks
    private StudentController studentController;

    @Mock
    private EntityManager entityManager;

    @Mock
    private StudentService studentService;

    private StudentDto studentDto;

    @BeforeEach
    void setUp() {
        studentDto = new StudentDto();
        studentDto.setStudentId(1L);
        studentDto.setEmail("test@example.com");
        studentDto.setPhoneNumber("+841234567890");
        studentDto.setStudentName("Nguyen");
        studentDto.setAge(19);
        studentDto.setAddress("18 HCM");
    }

    @AfterEach
    void clear(){
        // clear cache
        entityManager.clear();
    }

    @DisplayName("validate phoneNumber")
    @Test
    void testUpdateStudent_PhoneNumberInvalid() {
        studentDto.setPhoneNumber("123456"); // SAI format

        ResponseEntity<?> response = studentController.updateStudent(1L, studentDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Phone number is not valid", response.getBody());
    }

    @DisplayName("duplicate email when update")
    @Test
    void testUpdateStudent_EmailDuplicate() {
        StudentDto otherStudent = new StudentDto();
        otherStudent.setStudentId(1L);
        otherStudent.setEmail("test@example.com");
        otherStudent.setPhoneNumber("+841234567890");
        otherStudent.setStudentName("Nguyen");
        otherStudent.setAge(19);
        otherStudent.setAddress("18 HCM");

        when(studentService.getAllStudents()).thenReturn(List.of(otherStudent));

        ResponseEntity<?> response = studentController.updateStudent(1L, studentDto);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    void testUpdateStudent_SuccessfulUpdate() {
        when(studentService.getAllStudents()).thenReturn(List.of(studentDto));
        when(studentService.UpdateStudent(eq(1L), any(StudentDto.class))).thenReturn(studentDto);

        ResponseEntity<?> response = studentController.updateStudent(1L, studentDto);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(studentDto, response.getBody());
    }
}
