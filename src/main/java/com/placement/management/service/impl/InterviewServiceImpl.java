package com.placement.management.service.impl;

import com.placement.management.dto.InterviewDTO;
import com.placement.management.dto.InterviewRequestDTO;
import com.placement.management.dto.InterviewResultUpdateDTO;
import com.placement.management.entity.Application;
import com.placement.management.entity.Interview;
import com.placement.management.enums.ApplicationStatus;
import com.placement.management.enums.InterviewResult;
import com.placement.management.exception.ResourceNotFoundException;
import com.placement.management.repository.ApplicationRepository;
import com.placement.management.repository.InterviewRepository;
import com.placement.management.service.InterviewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class InterviewServiceImpl implements InterviewService {

    private final InterviewRepository interviewRepository;
    private final ApplicationRepository applicationRepository;
    private final ApplicationServiceImpl applicationService;

    public InterviewServiceImpl(InterviewRepository interviewRepository,
                                ApplicationRepository applicationRepository,
                                ApplicationServiceImpl applicationService) {
        this.interviewRepository = interviewRepository;
        this.applicationRepository = applicationRepository;
        this.applicationService = applicationService;
    }

    @Override
    public InterviewDTO scheduleInterview(InterviewRequestDTO requestDTO) {
        Application application = applicationRepository.findById(requestDTO.getApplicationId())
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with ID: " + requestDTO.getApplicationId()));

        Interview interview = new Interview();
        interview.setApplication(application);
        interview.setRoundName(requestDTO.getRoundName());
        interview.setInterviewDate(requestDTO.getInterviewDate());
        interview.setLocationLink(requestDTO.getLocationLink());
        interview.setResult(InterviewResult.PENDING);
        interview.setFeedback(requestDTO.getFeedback());

        // Automatically update application status to INTERVIEW_SCHEDULED
        application.setStatus(ApplicationStatus.INTERVIEW_SCHEDULED);
        applicationRepository.save(application);

        Interview saved = interviewRepository.save(interview);
        return mapEntityToDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public InterviewDTO getInterviewById(Long id) {
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interview not found with ID: " + id));
        return mapEntityToDTO(interview);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InterviewDTO> getAllInterviews() {
        return interviewRepository.findAll()
                .stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<InterviewDTO> getInterviewsByApplication(Long applicationId) {
        if (!applicationRepository.existsById(applicationId)) {
            throw new ResourceNotFoundException("Application not found with ID: " + applicationId);
        }
        return interviewRepository.findByApplicationId(applicationId)
                .stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InterviewDTO updateInterviewResult(Long id, InterviewResultUpdateDTO dto) {
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interview not found with ID: " + id));

        interview.setResult(dto.getResult());
        if (dto.getFeedback() != null && !dto.getFeedback().isBlank()) {
            interview.setFeedback(dto.getFeedback());
        }

        // Update corresponding application status based on interview outcome
        Application application = interview.getApplication();
        if (dto.getResult() == InterviewResult.PASSED) {
            application.setStatus(ApplicationStatus.SHORTLISTED);
        } else if (dto.getResult() == InterviewResult.FAILED) {
            application.setStatus(ApplicationStatus.REJECTED);
        }
        applicationRepository.save(application);

        Interview saved = interviewRepository.save(interview);
        return mapEntityToDTO(saved);
    }

    @Override
    public InterviewDTO updateInterview(Long id, InterviewRequestDTO requestDTO) {
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interview not found with ID: " + id));

        interview.setRoundName(requestDTO.getRoundName());
        interview.setInterviewDate(requestDTO.getInterviewDate());
        interview.setLocationLink(requestDTO.getLocationLink());
        if (requestDTO.getFeedback() != null) {
            interview.setFeedback(requestDTO.getFeedback());
        }

        Interview updated = interviewRepository.save(interview);
        return mapEntityToDTO(updated);
    }

    @Override
    public void deleteInterview(Long id) {
        if (!interviewRepository.existsById(id)) {
            throw new ResourceNotFoundException("Interview not found with ID: " + id);
        }
        interviewRepository.deleteById(id);
    }

    public InterviewDTO mapEntityToDTO(Interview entity) {
        return new InterviewDTO(
                entity.getId(),
                applicationService.mapEntityToDTO(entity.getApplication()),
                entity.getRoundName(),
                entity.getInterviewDate(),
                entity.getLocationLink(),
                entity.getResult(),
                entity.getFeedback()
        );
    }
}
