package com.placement.management.service.impl;

import com.placement.management.dto.CompanyDTO;
import com.placement.management.dto.JobDTO;
import com.placement.management.dto.JobRequestDTO;
import com.placement.management.dto.SkillDTO;
import com.placement.management.entity.Company;
import com.placement.management.entity.Job;
import com.placement.management.entity.Skill;
import com.placement.management.entity.Student;
import com.placement.management.exception.ResourceNotFoundException;
import com.placement.management.repository.CompanyRepository;
import com.placement.management.repository.JobRepository;
import com.placement.management.repository.SkillRepository;
import com.placement.management.repository.StudentRepository;
import com.placement.management.service.JobService;
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
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;
    private final SkillRepository skillRepository;
    private final StudentRepository studentRepository;

    public JobServiceImpl(JobRepository jobRepository, CompanyRepository companyRepository, SkillRepository skillRepository, StudentRepository studentRepository) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
        this.skillRepository = skillRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public JobDTO createJob(JobRequestDTO requestDTO) {
        Company company = companyRepository.findById(requestDTO.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with ID: " + requestDTO.getCompanyId()));

        Job job = new Job();
        mapRequestToEntity(requestDTO, job, company);

        Job saved = jobRepository.save(job);
        return mapEntityToDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public JobDTO getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with ID: " + id));
        return mapEntityToDTO(job);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobDTO> getAllJobs() {
        return jobRepository.findAll()
                .stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JobDTO> getAllJobsPaginated(Pageable pageable) {
        return jobRepository.findAll(pageable).map(this::mapEntityToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobDTO> getJobsByCompany(Long companyId) {
        if (!companyRepository.existsById(companyId)) {
            throw new ResourceNotFoundException("Company not found with ID: " + companyId);
        }
        return jobRepository.findByCompanyId(companyId)
                .stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobDTO> getEligibleJobsForStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + studentId));

        return jobRepository.findEligibleJobsForStudent(student.getCgpa())
                .stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public JobDTO updateJob(Long id, JobRequestDTO requestDTO) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with ID: " + id));

        Company company = companyRepository.findById(requestDTO.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with ID: " + requestDTO.getCompanyId()));

        mapRequestToEntity(requestDTO, job, company);
        Job updated = jobRepository.save(job);
        return mapEntityToDTO(updated);
    }

    @Override
    public JobDTO toggleJobStatus(Long id, Boolean active) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with ID: " + id));
        job.setActive(active);
        Job saved = jobRepository.save(job);
        return mapEntityToDTO(saved);
    }

    @Override
    public void deleteJob(Long id) {
        if (!jobRepository.existsById(id)) {
            throw new ResourceNotFoundException("Job not found with ID: " + id);
        }
        jobRepository.deleteById(id);
    }

    private void mapRequestToEntity(JobRequestDTO dto, Job entity, Company company) {
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setCompany(company);
        entity.setMinCgpa(dto.getMinCgpa());
        entity.setPackageAmount(dto.getPackageAmount());
        entity.setLocation(dto.getLocation());
        entity.setDeadline(dto.getDeadline());
        if (dto.getActive() != null) {
            entity.setActive(dto.getActive());
        }

        if (dto.getRequiredSkillIds() != null && !dto.getRequiredSkillIds().isEmpty()) {
            Set<Skill> skills = new HashSet<>(skillRepository.findAllById(dto.getRequiredSkillIds()));
            entity.setRequiredSkills(skills);
        }
    }

    public JobDTO mapEntityToDTO(Job entity) {
        CompanyDTO companyDTO = new CompanyDTO(
                entity.getCompany().getId(),
                entity.getCompany().getName(),
                entity.getCompany().getIndustry(),
                entity.getCompany().getEmail(),
                entity.getCompany().getPhone(),
                entity.getCompany().getWebsite(),
                entity.getCompany().getAddress(),
                entity.getCompany().getStatus()
        );

        Set<SkillDTO> skillDTOs = entity.getRequiredSkills().stream()
                .map(s -> new SkillDTO(s.getId(), s.getName(), s.getDescription()))
                .collect(Collectors.toSet());

        return new JobDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                companyDTO,
                entity.getMinCgpa(),
                entity.getPackageAmount(),
                entity.getLocation(),
                entity.getDeadline(),
                entity.getActive(),
                skillDTOs
        );
    }
}
