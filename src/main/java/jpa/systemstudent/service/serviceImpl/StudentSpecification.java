package jpa.systemstudent.service.serviceImpl;

import jpa.systemstudent.dto.StudentDto;
import jpa.systemstudent.entity.Student;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class StudentSpecification {

    public static Specification<Student> getStudentsByCriteria(StudentDto request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getStudentName() != null && !request.getStudentName().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("studentName")), "%" + request.getStudentName().toLowerCase() + "%"));
            }
            if (request.getEmail() != null && !request.getEmail().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("email")), "%" + request.getEmail().toLowerCase() + "%"));
            }
            if (request.getAddress() != null && !request.getAddress().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("address")), "%" + request.getAddress().toLowerCase() + "%"));
            }
            if (request.getAge() != null) {
                predicates.add(cb.equal(root.get("age"), request.getAge()));
            }
            // Trả về tất cả nếu không có điều kiện nào
            if (predicates.isEmpty()) {
                return cb.conjunction();
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
