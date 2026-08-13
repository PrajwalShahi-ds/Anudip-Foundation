package com.placement.management.service;

import com.placement.management.dto.ApplicationDTO;
import com.placement.management.dto.ApplicationRequestDTO;
import com.placement.management.enums.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ApplicationService {
    ApplicationDTO applyForJob(ApplicationRequestDTO requestDTO);
    ApplicationDTO getApplicationById(Long id);
    List<ApplicationDTO> getAllApplications();
    Page<ApplicationDTO> getAllApplicationsPaginated(Pageable pageable);
    List<ApplicationDTO> getApplicationsByStudent(Long studentId);
    List<ApplicationDTO> getApplicationsByJob(Long jobId);
    ApplicationDTO updateApplicationStatus(Long id, ApplicationStatus status);
    void deleteApplication(Long id);
}
