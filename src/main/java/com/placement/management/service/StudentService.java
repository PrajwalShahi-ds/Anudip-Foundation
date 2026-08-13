package com.placement.management.service;

import com.placement.management.dto.StudentDTO;
import com.placement.management.dto.StudentRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {
    StudentDTO createStudent(StudentRequestDTO requestDTO);
    StudentDTO getStudentById(Long id);
    List<StudentDTO> getAllStudents();
    Page<StudentDTO> getAllStudentsPaginated(Pageable pageable);
    Page<StudentDTO> searchStudents(String department, String name, Double minCgpa, Pageable pageable);
    StudentDTO updateStudent(Long id, StudentRequestDTO requestDTO);
    StudentDTO deactivateStudent(Long id);
    StudentDTO activateStudent(Long id);
    void deleteStudent(Long id);
}
