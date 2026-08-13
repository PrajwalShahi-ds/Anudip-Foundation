package com.placement.management.controller;

import com.placement.management.dto.JobDTO;
import com.placement.management.dto.JobRequestDTO;
import com.placement.management.service.JobService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<JobDTO> createJob(@Valid @RequestBody JobRequestDTO requestDTO) {
        JobDTO createdJob = jobService.createJob(requestDTO);
        return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long id) {
        JobDTO job = jobService.getJobById(id);
        return ResponseEntity.ok(job);
    }

    @GetMapping
    public ResponseEntity<List<JobDTO>> getAllJobs() {
        List<JobDTO> jobs = jobService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<JobDTO>> getAllJobsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return ResponseEntity.ok(jobService.getAllJobsPaginated(pageable));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<JobDTO>> getJobsByCompany(@PathVariable Long companyId) {
        List<JobDTO> jobs = jobService.getJobsByCompany(companyId);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/eligible/{studentId}")
    public ResponseEntity<List<JobDTO>> getEligibleJobsForStudent(@PathVariable Long studentId) {
        List<JobDTO> eligibleJobs = jobService.getEligibleJobsForStudent(studentId);
        return ResponseEntity.ok(eligibleJobs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobDTO> updateJob(
            @PathVariable Long id,
            @Valid @RequestBody JobRequestDTO requestDTO) {
        JobDTO updated = jobService.updateJob(id, requestDTO);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<JobDTO> toggleJobStatus(
            @PathVariable Long id,
            @RequestParam Boolean active) {
        JobDTO updated = jobService.toggleJobStatus(id, active);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }
}
