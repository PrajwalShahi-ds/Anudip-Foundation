package com.placement.management.dto;

public class DashboardStatsDTO {

    private long totalStudents;
    private long activeStudents;
    private long totalCompanies;
    private long activeCompanies;
    private long totalJobs;
    private long activeJobs;
    private long totalApplications;
    private long shortlistedApplications;
    private long totalPlacements;
    private double averagePackage;
    private double highestPackage;

    public DashboardStatsDTO() {
    }

    public DashboardStatsDTO(long totalStudents, long activeStudents, long totalCompanies, long activeCompanies, long totalJobs, long activeJobs, long totalApplications, long shortlistedApplications, long totalPlacements, double averagePackage, double highestPackage) {
        this.totalStudents = totalStudents;
        this.activeStudents = activeStudents;
        this.totalCompanies = totalCompanies;
        this.activeCompanies = activeCompanies;
        this.totalJobs = totalJobs;
        this.activeJobs = activeJobs;
        this.totalApplications = totalApplications;
        this.shortlistedApplications = shortlistedApplications;
        this.totalPlacements = totalPlacements;
        this.averagePackage = averagePackage;
        this.highestPackage = highestPackage;
    }

    public long getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(long totalStudents) {
        this.totalStudents = totalStudents;
    }

    public long getActiveStudents() {
        return activeStudents;
    }

    public void setActiveStudents(long activeStudents) {
        this.activeStudents = activeStudents;
    }

    public long getTotalCompanies() {
        return totalCompanies;
    }

    public void setTotalCompanies(long totalCompanies) {
        this.totalCompanies = totalCompanies;
    }

    public long getActiveCompanies() {
        return activeCompanies;
    }

    public void setActiveCompanies(long activeCompanies) {
        this.activeCompanies = activeCompanies;
    }

    public long getTotalJobs() {
        return totalJobs;
    }

    public void setTotalJobs(long totalJobs) {
        this.totalJobs = totalJobs;
    }

    public long getActiveJobs() {
        return activeJobs;
    }

    public void setActiveJobs(long activeJobs) {
        this.activeJobs = activeJobs;
    }

    public long getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(long totalApplications) {
        this.totalApplications = totalApplications;
    }

    public long getShortlistedApplications() {
        return shortlistedApplications;
    }

    public void setShortlistedApplications(long shortlistedApplications) {
        this.shortlistedApplications = shortlistedApplications;
    }

    public long getTotalPlacements() {
        return totalPlacements;
    }

    public void setTotalPlacements(long totalPlacements) {
        this.totalPlacements = totalPlacements;
    }

    public double getAveragePackage() {
        return averagePackage;
    }

    public void setAveragePackage(double averagePackage) {
        this.averagePackage = averagePackage;
    }

    public double getHighestPackage() {
        return highestPackage;
    }

    public void setHighestPackage(double highestPackage) {
        this.highestPackage = highestPackage;
    }
}
