package com.placement.management.entity;

import com.placement.management.enums.ApplicationStatus;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(
    name = "applications",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id", "job_id"})
    }
)
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @Column(name = "apply_date", nullable = false)
    private LocalDate applyDate = LocalDate.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ApplicationStatus status = ApplicationStatus.APPLIED;

    public Application() {
    }

    public Application(Long id, Student student, Job job, LocalDate applyDate, ApplicationStatus status) {
        this.id = id;
        this.student = student;
        this.job = job;
        this.applyDate = applyDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public LocalDate getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(LocalDate applyDate) {
        this.applyDate = applyDate;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}
