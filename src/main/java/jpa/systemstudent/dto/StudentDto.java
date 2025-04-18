package jpa.systemstudent.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
    private Long studentId;

    private String studentName;

    private Integer age;

    private String email;

    private String address;

    private LocalDate birthday;

    private String phoneNumber;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    private String createdBy;

    private String lastModifiedBy;

    public StudentDto(String studentName, int age, String email, String address, LocalDate birthday) {
        this.studentName = studentName;
        this.age = age;
        this.email = email;
        this.address = address;
        this.birthday = birthday;
    }
}
