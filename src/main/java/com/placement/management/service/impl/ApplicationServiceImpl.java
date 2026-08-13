package com.placement.management.service.impl;

import com.placement.management.dto.ApplicationDTO;
import com.placement.management.dto.ApplicationRequestDTO;
import com.placement.management.entity.Application;
import com.placement.management.entity.Job;
import com.placement.management.entity.Student;
import com.placement.management.enums.ApplicationStatus;
import com.placement.management.exception.DuplicateResourceException;
import com.placement.management.exception.IneligibleStudentException;
import com.placement.management.exception.ResourceNotFoundException;
import com.placement.management.repository.ApplicationRepository;
import com.placement.management.repository.JobRepository;
import com.placement.management.repository.StudentRepository;
import com.placement.management.service.ApplicationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final StudentRepository studentRepository;
    private final JobRepository jobRepository;
    private final StudentServiceImpl studentService;
    private final JobServiceImpl jobService;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository,
                                  StudentRepository studentRepository,
                                  JobRepository jobRepository,
                                  StudentServiceImpl studentService,
                                  JobServiceImpl jobService) {
        this.applicationRepository = applicationRepository;
        this.studentRepository = studentRepository;
        this.jobRepository = jobRepository;
        this.studentService = studentService;
        this.jobService = jobService;
    }

    @Override
    public ApplicationDTO applyForJob(ApplicationRequestDTO requestDTO) {
        Student student = studentRepository.findById(requestDTO.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + requestDTO.getStudentId()));

        Job job = jobRepository.findById(requestDTO.getJobId())
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with ID: " + requestDTO.getJobId()));

        if (!student.getActive()) {
            throw new IneligibleStudentException("Inactive student (ID: " + student.getId() + ") cannot apply for jobs");
        }

        if (!job.getActive()) {
            throw new IneligibleStudentException("Job opening '" + job.getTitle() + "' is currently inactive/closed");
        }

        if (applicationRepository.existsByStudentIdAndJobId(student.getId(), job.getId())) {
            throw new DuplicateResourceException("Student (ID: " + student.getId() + ") has already applied for Job: '" + job.getTitle() + "'");
        }

        // Eligibility check: CGPA criteria validation
        if (student.getCgpa() < job.getMinCgpa()) {
            throw new IneligibleStudentException(String.format(
                    "Student CGPA (%.2f) is below the minimum required CGPA (%.2f) for job '%s'",
                    student.getCgpa(), job.getMinCgpa(), job.getTitle()
            ));
        }

        Application application = new Application();
        application.setStudent(student);
        application.setJob(job);
        application.setApplyDate(LocalDate.now());
        application.setStatus(ApplicationStatus.APPLIED);

        Application saved = applicationRepository.save(application);
        return mapEntityToDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ApplicationDTO getApplicationById(Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with ID: " + id));
        return mapEntityToDTO(application);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationDTO> getAllApplications() {
        return applicationRepository.findAll()
                .stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationDTO> getAllApplicationsPaginated(Pageable pageable) {
        return applicationRepository.findAll(pageable).map(this::mapEntityToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationDTO> getApplicationsByStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found with ID: " + studentId);
        }
        return applicationRepository.findByStudentId(studentId)
                .stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationDTO> getApplicationsByJob(Long jobId) {
        if (!jobRepository.existsById(jobId)) {
            throw new ResourceNotFoundException("Job not found with ID: " + jobId);
        }
        return applicationRepository.findByJobId(jobId)
                .stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ApplicationDTO updateApplicationStatus(Long id, ApplicationStatus status) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with ID: " + id));
        application.setStatus(status);
        Application updated = applicationRepository.save(application);
        return mapEntityToDTO(updated);
    }

    @Override
    public void deleteApplication(Long id) {
        if (!applicationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Application not found with ID: " + id);
        }
        applicationRepository.deleteById(id);
    }

    public ApplicationDTO mapEntityToDTO(Application entity) {
        return new ApplicationDTO(
                entity.getId(),
                studentService.mapEntityToDTO(entity.getStudent()),
                jobService.mapEntityToDTO(entity.getJob()),
                entity.getApplyDate(),
                entity.getStatus()
        );
    }
}
