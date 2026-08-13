package com.placement.management.dto;

import java.time.LocalDate;
import java.util.Set;

public class JobDTO {

    private Long id;
    private String title;
    private String description;
    private CompanyDTO company;
    private Double minCgpa;
    private Double packageAmount;
    private String location;
    private LocalDate deadline;
    private Boolean active;
    private Set<SkillDTO> requiredSkills;

    public JobDTO() {
    }

    public JobDTO(Long id, String title, String description, CompanyDTO company, Double minCgpa, Double packageAmount, String location, LocalDate deadline, Boolean active, Set<SkillDTO> requiredSkills) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.company = company;
        this.minCgpa = minCgpa;
        this.packageAmount = packageAmount;
        this.location = location;
        this.deadline = deadline;
        this.active = active;
        this.requiredSkills = requiredSkills;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
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

    public Set<SkillDTO> getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(Set<SkillDTO> requiredSkills) {
        this.requiredSkills = requiredSkills;
    }
}
