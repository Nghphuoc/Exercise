package jpa.systemstudent.exception;

public class StudentNotFoundException extends Exception{
    public StudentNotFoundException(Long id){
        super("Student with id = " + id + " has not existed in DB");
    }
}
