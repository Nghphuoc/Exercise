package jpa.systemstudent.entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Table(name = "student")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;

    @Column(name = "studentName")
    private String studentName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "email")
    @NotBlank
    @Email
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    public Student(String studentName, int age, String email, String address, LocalDate birthday) {
        this.studentName = studentName;
        this.age = age;
        this.email = email;
        this.address = address;
        this.birthday = birthday;
    }
}
