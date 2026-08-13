package com.placement.management.service.impl;

import com.placement.management.dto.SkillDTO;
import com.placement.management.dto.StudentDTO;
import com.placement.management.dto.StudentRequestDTO;
import com.placement.management.entity.Skill;
import com.placement.management.entity.Student;
import com.placement.management.exception.DuplicateResourceException;
import com.placement.management.exception.ResourceNotFoundException;
import com.placement.management.repository.SkillRepository;
import com.placement.management.repository.StudentRepository;
import com.placement.management.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final SkillRepository skillRepository;

    public StudentServiceImpl(StudentRepository studentRepository, SkillRepository skillRepository) {
        this.studentRepository = studentRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    public StudentDTO createStudent(StudentRequestDTO requestDTO) {
        if (studentRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateResourceException("Student with email '" + requestDTO.getEmail() + "' already exists");
        }

        Student student = new Student();
        mapRequestToEntity(requestDTO, student);

        Student savedStudent = studentRepository.save(student);
        return mapEntityToDTO(savedStudent);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
        return mapEntityToDTO(student);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentDTO> getAllStudentsPaginated(Pageable pageable) {
        return studentRepository.findAll(pageable).map(this::mapEntityToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentDTO> searchStudents(String department, String name, Double minCgpa, Pageable pageable) {
        return studentRepository.searchStudents(department, name, minCgpa, pageable).map(this::mapEntityToDTO);
    }

    @Override
    public StudentDTO updateStudent(Long id, StudentRequestDTO requestDTO) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));

        if (studentRepository.existsByEmailAndIdNot(requestDTO.getEmail(), id)) {
            throw new DuplicateResourceException("Email '" + requestDTO.getEmail() + "' is already in use by another student");
        }

        mapRequestToEntity(requestDTO, student);
        Student updatedStudent = studentRepository.save(student);
        return mapEntityToDTO(updatedStudent);
    }

    @Override
    public StudentDTO deactivateStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
        student.setActive(false);
        Student saved = studentRepository.save(student);
        return mapEntityToDTO(saved);
    }

    @Override
    public StudentDTO activateStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
        student.setActive(true);
        Student saved = studentRepository.save(student);
        return mapEntityToDTO(saved);
    }

    @Override
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student not found with ID: " + id);
        }
        studentRepository.deleteById(id);
    }

    private void mapRequestToEntity(StudentRequestDTO dto, Student entity) {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setDepartment(dto.getDepartment());
        entity.setCgpa(dto.getCgpa());
        entity.setPassingYear(dto.getPassingYear());
        if (dto.getActive() != null) {
            entity.setActive(dto.getActive());
        }
        entity.setResumeUrl(dto.getResumeUrl());

        if (dto.getSkillIds() != null && !dto.getSkillIds().isEmpty()) {
            Set<Skill> skills = new HashSet<>(skillRepository.findAllById(dto.getSkillIds()));
            entity.setSkills(skills);
        }
    }

    public StudentDTO mapEntityToDTO(Student entity) {
        Set<SkillDTO> skillDTOs = entity.getSkills().stream()
                .map(s -> new SkillDTO(s.getId(), s.getName(), s.getDescription()))
                .collect(Collectors.toSet());

        return new StudentDTO(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getDepartment(),
                entity.getCgpa(),
                entity.getPassingYear(),
                entity.getActive(),
                entity.getResumeUrl(),
                skillDTOs
        );
    }
}
