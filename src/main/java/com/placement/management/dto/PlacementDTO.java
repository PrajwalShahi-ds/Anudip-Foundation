package com.placement.management.dto;

import java.time.LocalDate;

public class PlacementDTO {

    private Long id;
    private StudentDTO student;
    private CompanyDTO company;
    private JobDTO job;
    private Double packageAmount;
    private LocalDate joiningDate;
    private String placementStatus;

    public PlacementDTO() {
    }

    public PlacementDTO(Long id, StudentDTO student, CompanyDTO company, JobDTO job, Double packageAmount, LocalDate joiningDate, String placementStatus) {
        this.id = id;
        this.student = student;
        this.company = company;
        this.job = job;
        this.packageAmount = packageAmount;
        this.joiningDate = joiningDate;
        this.placementStatus = placementStatus;
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

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    public JobDTO getJob() {
        return job;
    }

    public void setJob(JobDTO job) {
        this.job = job;
    }

    public Double getPackageAmount() {
        return packageAmount;
    }

    public void setPackageAmount(Double packageAmount) {
        this.packageAmount = packageAmount;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getPlacementStatus() {
        return placementStatus;
    }

    public void setPlacementStatus(String placementStatus) {
        this.placementStatus = placementStatus;
    }
}
