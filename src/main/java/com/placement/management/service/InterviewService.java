package com.placement.management.service;

import com.placement.management.dto.InterviewDTO;
import com.placement.management.dto.InterviewRequestDTO;
import com.placement.management.dto.InterviewResultUpdateDTO;

import java.util.List;

public interface InterviewService {
    InterviewDTO scheduleInterview(InterviewRequestDTO requestDTO);
    InterviewDTO getInterviewById(Long id);
    List<InterviewDTO> getAllInterviews();
    List<InterviewDTO> getInterviewsByApplication(Long applicationId);
    InterviewDTO updateInterviewResult(Long id, InterviewResultUpdateDTO dto);
    InterviewDTO updateInterview(Long id, InterviewRequestDTO requestDTO);
    void deleteInterview(Long id);
}
