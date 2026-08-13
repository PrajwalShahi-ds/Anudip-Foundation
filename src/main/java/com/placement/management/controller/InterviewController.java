package com.placement.management.controller;

import com.placement.management.dto.InterviewDTO;
import com.placement.management.dto.InterviewRequestDTO;
import com.placement.management.dto.InterviewResultUpdateDTO;
import com.placement.management.service.InterviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interviews")
@CrossOrigin(origins = "*")
public class InterviewController {

    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @PostMapping
    public ResponseEntity<InterviewDTO> scheduleInterview(@Valid @RequestBody InterviewRequestDTO requestDTO) {
        InterviewDTO createdInterview = interviewService.scheduleInterview(requestDTO);
        return new ResponseEntity<>(createdInterview, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterviewDTO> getInterviewById(@PathVariable Long id) {
        InterviewDTO interview = interviewService.getInterviewById(id);
        return ResponseEntity.ok(interview);
    }

    @GetMapping
    public ResponseEntity<List<InterviewDTO>> getAllInterviews() {
        List<InterviewDTO> interviews = interviewService.getAllInterviews();
        return ResponseEntity.ok(interviews);
    }

    @GetMapping("/application/{applicationId}")
    public ResponseEntity<List<InterviewDTO>> getInterviewsByApplication(@PathVariable Long applicationId) {
        List<InterviewDTO> interviews = interviewService.getInterviewsByApplication(applicationId);
        return ResponseEntity.ok(interviews);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InterviewDTO> updateInterview(
            @PathVariable Long id,
            @Valid @RequestBody InterviewRequestDTO requestDTO) {
        InterviewDTO updated = interviewService.updateInterview(id, requestDTO);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/result")
    public ResponseEntity<InterviewDTO> updateInterviewResult(
            @PathVariable Long id,
            @Valid @RequestBody InterviewResultUpdateDTO resultUpdateDTO) {
        InterviewDTO updated = interviewService.updateInterviewResult(id, resultUpdateDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInterview(@PathVariable Long id) {
        interviewService.deleteInterview(id);
        return ResponseEntity.noContent().build();
    }
}
