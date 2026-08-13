package com.placement.management.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

public class JobRequestDTO {

    @NotBlank(message = "Job title is required")
    private String title;

    private String description;

    @NotNull(message = "Company ID is required")
    private Long companyId;

    @NotNull(message = "Minimum CGPA requirement is required")
    @DecimalMin(value = "0.0", message = "Minimum CGPA cannot be negative")
    @DecimalMax(value = "10.0", message = "Minimum CGPA cannot exceed 10.0")
    private Double minCgpa;

    @NotNull(message = "Package amount is required")
    @Positive(message = "Package amount must be positive")
    private Double packageAmount;

    private String location;

    @NotNull(message = "Application deadline date is required")
    @FutureOrPresent(message = "Deadline must be today or in the future")
    private LocalDate deadline;

    private Boolean active = true;

    private Set<Long> requiredSkillIds;

    public JobRequestDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Double getMinCgpa() {
        return minCgpa;
    }

    public void setMinCgpa(Double minCgpa) {
        this.minCgpa = minCgpa;
    }

    public Double getPackageAmount() {
        return packageAmount;
    }

    public void setPackageAmount(Double packageAmount) {
        this.packageAmount = packageAmount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Long> getRequiredSkillIds() {
        return requiredSkillIds;
    }

    public void setRequiredSkillIds(Set<Long> requiredSkillIds) {
        this.requiredSkillIds = requiredSkillIds;
    }
}
