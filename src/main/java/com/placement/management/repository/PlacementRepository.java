package com.placement.management.repository;

import com.placement.management.entity.Placement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlacementRepository extends JpaRepository<Placement, Long> {

    Optional<Placement> findByStudentId(Long studentId);

    boolean existsByStudentId(Long studentId);

    List<Placement> findByCompanyId(Long companyId);

    @Query("SELECT AVG(p.packageAmount) FROM Placement p")
    Double findAveragePackage();

    @Query("SELECT MAX(p.packageAmount) FROM Placement p")
    Double findHighestPackage();
}
