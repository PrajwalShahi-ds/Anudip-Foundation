package com.placement.management.dto;

import com.placement.management.enums.InterviewResult;
import java.time.LocalDateTime;

public class InterviewDTO {

    private Long id;
    private ApplicationDTO application;
    private String roundName;
    private LocalDateTime interviewDate;
    private String locationLink;
    private InterviewResult result;
    private String feedback;

    public InterviewDTO() {
    }

    public InterviewDTO(Long id, ApplicationDTO application, String roundName, LocalDateTime interviewDate, String locationLink, InterviewResult result, String feedback) {
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

    public ApplicationDTO getApplication() {
        return application;
    }

    public void setApplication(ApplicationDTO application) {
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
