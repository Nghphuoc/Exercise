package jpa.systemstudent.entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "student")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    @Column(name = "studentName")
    private String studentName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "email",unique = true)
    @NotBlank
    @Email
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @CreatedDate
    @Column(name = "createdDate")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "lastModifiedDate")
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    @Column(name = "createdBy")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "lastModifiedBy")
    private String lastModifiedBy;

    public Student(String studentName, int age, String email, String address, LocalDate birthday) {
        this.studentName = studentName;
        this.age = age;
        this.email = email;
        this.address = address;
        this.birthday = birthday;
    }
}
