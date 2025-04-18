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
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
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
        PodamFactory factory = new PodamFactoryImpl();
        StudentDto student = factory.manufacturePojo(StudentDto.class);

        when(studentService.getAllStudents()).thenReturn(List.of(student));

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

    @Test
    void testGetAllStudents() {
        List<StudentDto> studentDtos = Arrays.asList(studentDto);

        // Mock method to return the list of students
        when(studentService.getAllStudents()).thenReturn(studentDtos);

        // Call the method in controller directly
        ResponseEntity<List<StudentDto>> response = studentController.getAllStudents();

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Nguyen", response.getBody().get(0).getStudentName());
    }

    @Test
    void testGetStudentById() {
        // Mock method to return a student by id
        when(studentService.getStudent(1L)).thenReturn(studentDto);

        // Call the method in controller directly
        ResponseEntity<?> response = studentController.getStudentById(1L);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Nguyen", ((StudentDto) response.getBody()).getStudentName());
    }

    @Test
    void testCreateStudent() {
        // Mock the service method to save the student
        when(studentService.saveStudent(studentDto)).thenReturn(studentDto);

        // Call the method in controller directly
        ResponseEntity<?> response = studentController.createStudent(studentDto);

        // Assert the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Nguyen", ((StudentDto) response.getBody()).getStudentName());
    }

    @Test
    void testCreateStudentWithInvalidPhoneNumber() {
        // Modify studentDto to have an invalid phone number
        studentDto.setPhoneNumber("invalidPhoneNumber");

        // Call the method in controller directly
        ResponseEntity<?> response = studentController.createStudent(studentDto);

        // Assert the response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Phone number is not valid", response.getBody());
    }

    @Test
    void testDeleteStudent() {
        // Mock the service method to delete the student
        doNothing().when(studentService).deleteStudent(1L);

        // Call the method in controller directly
        ResponseEntity<?> response = studentController.deleteStudent(1L);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Delete successfully!!!", response.getBody());
    }
}
