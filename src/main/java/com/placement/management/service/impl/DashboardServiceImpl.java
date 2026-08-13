package com.placement.management.service.impl;

import com.placement.management.dto.DashboardStatsDTO;
import com.placement.management.enums.ApplicationStatus;
import com.placement.management.enums.CompanyStatus;
import com.placement.management.repository.*;
import com.placement.management.service.DashboardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    private final StudentRepository studentRepository;
    private final CompanyRepository companyRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final PlacementRepository placementRepository;

    public DashboardServiceImpl(StudentRepository studentRepository,
                                 CompanyRepository companyRepository,
                                 JobRepository jobRepository,
                                 ApplicationRepository applicationRepository,
                                 PlacementRepository placementRepository) {
        this.studentRepository = studentRepository;
        this.companyRepository = companyRepository;
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
        this.placementRepository = placementRepository;
    }

    @Override
    public DashboardStatsDTO getDashboardStats() {
        long totalStudents = studentRepository.count();
        long activeStudents = studentRepository.countByActiveTrue();
        long totalCompanies = companyRepository.count();
        long activeCompanies = companyRepository.countByStatus(CompanyStatus.ACTIVE);
        long totalJobs = jobRepository.count();
        long activeJobs = jobRepository.countByActiveTrue();
        long totalApplications = applicationRepository.count();
        long shortlistedApplications = applicationRepository.countByStatus(ApplicationStatus.SHORTLISTED);
        long totalPlacements = placementRepository.count();

        Double avgPkg = placementRepository.findAveragePackage();
        Double maxPkg = placementRepository.findHighestPackage();

        double averagePackage = (avgPkg != null) ? Math.round(avgPkg * 100.0) / 100.0 : 0.0;
        double highestPackage = (maxPkg != null) ? maxPkg : 0.0;

        return new DashboardStatsDTO(
                totalStudents,
                activeStudents,
                totalCompanies,
                activeCompanies,
                totalJobs,
                activeJobs,
                totalApplications,
                shortlistedApplications,
                totalPlacements,
                averagePackage,
                highestPackage
        );
    }
}
