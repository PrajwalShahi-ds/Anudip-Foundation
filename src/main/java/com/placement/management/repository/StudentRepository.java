package com.placement.management.repository;

import com.placement.management.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    Page<Student> findByActive(Boolean active, Pageable pageable);

    @Query("SELECT s FROM Student s WHERE " +
           "(:department IS NULL OR LOWER(s.department) LIKE LOWER(CONCAT('%', :department, '%'))) AND " +
           "(:name IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:minCgpa IS NULL OR s.cgpa >= :minCgpa)")
    Page<Student> searchStudents(@Param("department") String department,
                                @Param("name") String name,
                                @Param("minCgpa") Double minCgpa,
                                Pageable pageable);

    long countByActiveTrue();
}
