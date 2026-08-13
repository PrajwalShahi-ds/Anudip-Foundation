package com.placement.management.dto;

import com.placement.management.enums.ApplicationStatus;
import java.time.LocalDate;

public class ApplicationDTO {

    private Long id;
    private StudentDTO student;
    private JobDTO job;
    private LocalDate applyDate;
    private ApplicationStatus status;

    public ApplicationDTO() {
    }

    public ApplicationDTO(Long id, StudentDTO student, JobDTO job, LocalDate applyDate, ApplicationStatus status) {
        this.id = id;
        this.student = student;
        this.job = job;
        this.applyDate = applyDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StudentDTO getStudent() {
        return student;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    public JobDTO getJob() {
        return job;
    }

    public void setJob(JobDTO job) {
        this.job = job;
    }

    public LocalDate getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(LocalDate applyDate) {
        this.applyDate = applyDate;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}
