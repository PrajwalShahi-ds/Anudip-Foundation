package com.placement.management.service;

import com.placement.management.dto.JobDTO;
import com.placement.management.dto.JobRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobService {
    JobDTO createJob(JobRequestDTO requestDTO);
    JobDTO getJobById(Long id);
    List<JobDTO> getAllJobs();
    Page<JobDTO> getAllJobsPaginated(Pageable pageable);
    List<JobDTO> getJobsByCompany(Long companyId);
    List<JobDTO> getEligibleJobsForStudent(Long studentId);
    JobDTO updateJob(Long id, JobRequestDTO requestDTO);
    JobDTO toggleJobStatus(Long id, Boolean active);
    void deleteJob(Long id);
}
