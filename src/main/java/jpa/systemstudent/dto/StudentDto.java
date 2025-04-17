package jpa.systemstudent.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
    private int studentId;

    private String studentName;

    private Integer age;

    private String email;

    private String address;

    private LocalDate birthday;

    private String phoneNumber;

    public StudentDto(String studentName, int age, String email, String address, LocalDate birthday) {
        this.studentName = studentName;
        this.age = age;
        this.email = email;
        this.address = address;
        this.birthday = birthday;
    }
}
