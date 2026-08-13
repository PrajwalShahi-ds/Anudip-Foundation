package com.placement.management.repository;

import com.placement.management.entity.Interview;
import com.placement.management.enums.InterviewResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {

    List<Interview> findByApplicationId(Long applicationId);

    List<Interview> findByResult(InterviewResult result);
}
