package com.placement.management.service.impl;

import com.placement.management.dto.PlacementDTO;
import com.placement.management.dto.PlacementRequestDTO;
import com.placement.management.entity.Application;
import com.placement.management.entity.Company;
import com.placement.management.entity.Job;
import com.placement.management.entity.Placement;
import com.placement.management.entity.Student;
import com.placement.management.enums.ApplicationStatus;
import com.placement.management.exception.DuplicateResourceException;
import com.placement.management.exception.ResourceNotFoundException;
import com.placement.management.repository.ApplicationRepository;
import com.placement.management.repository.CompanyRepository;
import com.placement.management.repository.JobRepository;
import com.placement.management.repository.PlacementRepository;
import com.placement.management.repository.StudentRepository;
import com.placement.management.service.PlacementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlacementServiceImpl implements PlacementService {

    private final PlacementRepository placementRepository;
    private final StudentRepository studentRepository;
    private final CompanyRepository companyRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final StudentServiceImpl studentService;
    private final CompanyServiceImpl companyService;
    private final JobServiceImpl jobService;

    public PlacementServiceImpl(PlacementRepository placementRepository,
                                StudentRepository studentRepository,
                                CompanyRepository companyRepository,
                                JobRepository jobRepository,
                                ApplicationRepository applicationRepository,
                                StudentServiceImpl studentService,
                                CompanyServiceImpl companyService,
                                JobServiceImpl jobService) {
        this.placementRepository = placementRepository;
        this.studentRepository = studentRepository;
        this.companyRepository = companyRepository;
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
        this.studentService = studentService;
        this.companyService = companyService;
        this.jobService = jobService;
    }

    @Override
    public PlacementDTO createPlacement(PlacementRequestDTO requestDTO) {
        Student student = studentRepository.findById(requestDTO.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + requestDTO.getStudentId()));

        Company company = companyRepository.findById(requestDTO.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with ID: " + requestDTO.getCompanyId()));

        Job job = jobRepository.findById(requestDTO.getJobId())
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with ID: " + requestDTO.getJobId()));

        if (placementRepository.existsByStudentId(student.getId())) {
            throw new DuplicateResourceException("Student (ID: " + student.getId() + ") already has an active placement record!");
        }

        // Update corresponding application status to SELECTED if application exists
        Optional<Application> appOpt = applicationRepository.findByStudentIdAndJobId(student.getId(), job.getId());
        if (appOpt.isPresent()) {
            Application application = appOpt.get();
            application.setStatus(ApplicationStatus.SELECTED);
            applicationRepository.save(application);
        }

        Placement placement = new Placement();
        placement.setStudent(student);
        placement.setCompany(company);
        placement.setJob(job);
        placement.setPackageAmount(requestDTO.getPackageAmount());
        placement.setJoiningDate(requestDTO.getJoiningDate() != null ? requestDTO.getJoiningDate() : LocalDate.now().plusMonths(3));
        if (requestDTO.getPlacementStatus() != null) {
            placement.setPlacementStatus(requestDTO.getPlacementStatus());
        }

        Placement saved = placementRepository.save(placement);
        return mapEntityToDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PlacementDTO getPlacementById(Long id) {
        Placement placement = placementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Placement record not found with ID: " + id));
        return mapEntityToDTO(placement);
    }

    @Override
    @Transactional(readOnly = true)
    public PlacementDTO getPlacementByStudentId(Long studentId) {
        Placement placement = placementRepository.findByStudentId(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Placement record not found for Student ID: " + studentId));
        return mapEntityToDTO(placement);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlacementDTO> getAllPlacements() {
        return placementRepository.findAll()
                .stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlacementDTO> getPlacementsByCompanyId(Long companyId) {
        if (!companyRepository.existsById(companyId)) {
            throw new ResourceNotFoundException("Company not found with ID: " + companyId);
        }
        return placementRepository.findByCompanyId(companyId)
                .stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deletePlacement(Long id) {
        if (!placementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Placement record not found with ID: " + id);
        }
        placementRepository.deleteById(id);
    }

    public PlacementDTO mapEntityToDTO(Placement entity) {
        return new PlacementDTO(
                entity.getId(),
                studentService.mapEntityToDTO(entity.getStudent()),
                companyService.mapEntityToDTO(entity.getCompany()),
                jobService.mapEntityToDTO(entity.getJob()),
                entity.getPackageAmount(),
                entity.getJoiningDate(),
                entity.getPlacementStatus()
        );
    }
}
