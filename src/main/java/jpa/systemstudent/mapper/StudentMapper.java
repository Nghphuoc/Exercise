package jpa.systemstudent.mapper;

import jpa.systemstudent.dto.StudentDto;
import jpa.systemstudent.entity.Student;

public class StudentMapper {
    public static Student mapStudentDto(StudentDto studentDto) {
        return new Student(
                studentDto.getStudentId(),
                studentDto.getStudentName(),
                studentDto.getAge(),
                studentDto.getEmail(),
                studentDto.getAddress(),
                studentDto.getBirthday(),
                studentDto.getPhoneNumber(),
                studentDto.getCreatedDate(),
                studentDto.getLastModifiedDate(),
                studentDto.getCreatedBy(),
                studentDto.getLastModifiedBy()
        );
    }

    public static StudentDto mapStudent(Student student) {
        return new StudentDto(
                student.getStudentId(),
                student.getStudentName(),
                student.getAge(),
                student.getEmail(),
                student.getAddress(),
                student.getBirthday(),
                student.getPhoneNumber(),
                student.getCreatedDate(),
                student.getLastModifiedDate(),
                student.getCreatedBy(),
                student.getLastModifiedBy()
        );
    }
}
