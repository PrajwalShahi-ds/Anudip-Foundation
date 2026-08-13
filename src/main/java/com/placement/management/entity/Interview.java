package com.placement.management.entity;

import com.placement.management.enums.InterviewResult;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "interviews")
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @Column(name = "round_name", nullable = false, length = 100)
    private String roundName;

    @Column(name = "interview_date", nullable = false)
    private LocalDateTime interviewDate;

    @Column(name = "location_link", length = 255)
    private String locationLink;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private InterviewResult result = InterviewResult.PENDING;

    @Column(columnDefinition = "TEXT")
    private String feedback;

    public Interview() {
    }

    public Interview(Long id, Application application, String roundName, LocalDateTime interviewDate, String locationLink, InterviewResult result, String feedback) {
        this.id = id;
        this.application = application;
        this.roundName = roundName;
        this.interviewDate = interviewDate;
        this.locationLink = locationLink;
        this.result = result;
        this.feedback = feedback;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public String getRoundName() {
        return roundName;
    }

    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }

    public LocalDateTime getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(LocalDateTime interviewDate) {
        this.interviewDate = interviewDate;
    }

    public String getLocationLink() {
        return locationLink;
    }

    public void setLocationLink(String locationLink) {
        this.locationLink = locationLink;
    }

    public InterviewResult getResult() {
        return result;
    }

    public void setResult(InterviewResult result) {
        this.result = result;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
