package com.placement.management.repository;

import com.placement.management.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByCompanyId(Long companyId);

    Page<Job> findByActive(Boolean active, Pageable pageable);

    @Query("SELECT j FROM Job j WHERE j.active = true AND j.minCgpa <= :studentCgpa")
    List<Job> findEligibleJobsForStudent(@Param("studentCgpa") Double studentCgpa);

    long countByActiveTrue();
}
