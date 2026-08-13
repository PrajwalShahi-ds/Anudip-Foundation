package com.placement.management.dto;

import java.util.Set;

public class StudentDTO {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String department;
    private Double cgpa;
    private Integer passingYear;
    private Boolean active;
    private String resumeUrl;
    private Set<SkillDTO> skills;

    public StudentDTO() {
    }

    public StudentDTO(Long id, String name, String email, String phone, String department, Double cgpa, Integer passingYear, Boolean active, String resumeUrl, Set<SkillDTO> skills) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.cgpa = cgpa;
        this.passingYear = passingYear;
        this.active = active;
        this.resumeUrl = resumeUrl;
        this.skills = skills;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Double getCgpa() {
        return cgpa;
    }

    public void setCgpa(Double cgpa) {
        this.cgpa = cgpa;
    }

    public Integer getPassingYear() {
        return passingYear;
    }

    public void setPassingYear(Integer passingYear) {
        this.passingYear = passingYear;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }

    public Set<SkillDTO> getSkills() {
        return skills;
    }

    public void setSkills(Set<SkillDTO> skills) {
        this.skills = skills;
    }
}
