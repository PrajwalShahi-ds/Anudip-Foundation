package com.placement.management.dto;

import com.placement.management.enums.ApplicationStatus;
import jakarta.validation.constraints.NotNull;

public class ApplicationStatusUpdateDTO {

    @NotNull(message = "Application status is required")
    private ApplicationStatus status;

    public ApplicationStatusUpdateDTO() {
    }

    public ApplicationStatusUpdateDTO(ApplicationStatus status) {
        this.status = status;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}
