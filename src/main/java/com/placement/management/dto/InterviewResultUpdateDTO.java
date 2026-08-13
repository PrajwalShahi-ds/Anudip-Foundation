package com.placement.management.dto;

import com.placement.management.enums.InterviewResult;
import jakarta.validation.constraints.NotNull;

public class InterviewResultUpdateDTO {

    @NotNull(message = "Interview result status is required")
    private InterviewResult result;

    private String feedback;

    public InterviewResultUpdateDTO() {
    }

    public InterviewResultUpdateDTO(InterviewResult result, String feedback) {
        this.result = result;
        this.feedback = feedback;
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
