package com.placement.management.controller;

import com.placement.management.dto.PlacementDTO;
import com.placement.management.dto.PlacementRequestDTO;
import com.placement.management.service.PlacementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/placements")
@CrossOrigin(origins = "*")
public class PlacementController {

    private final PlacementService placementService;

    public PlacementController(PlacementService placementService) {
        this.placementService = placementService;
    }

    @PostMapping
    public ResponseEntity<PlacementDTO> createPlacement(@Valid @RequestBody PlacementRequestDTO requestDTO) {
        PlacementDTO createdPlacement = placementService.createPlacement(requestDTO);
        return new ResponseEntity<>(createdPlacement, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlacementDTO> getPlacementById(@PathVariable Long id) {
        PlacementDTO placement = placementService.getPlacementById(id);
        return ResponseEntity.ok(placement);
    }

    @GetMapping
    public ResponseEntity<List<PlacementDTO>> getAllPlacements() {
        List<PlacementDTO> placements = placementService.getAllPlacements();
        return ResponseEntity.ok(placements);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<PlacementDTO> getPlacementByStudentId(@PathVariable Long studentId) {
        PlacementDTO placement = placementService.getPlacementByStudentId(studentId);
        return ResponseEntity.ok(placement);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<PlacementDTO>> getPlacementsByCompanyId(@PathVariable Long companyId) {
        List<PlacementDTO> placements = placementService.getPlacementsByCompanyId(companyId);
        return ResponseEntity.ok(placements);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlacement(@PathVariable Long id) {
        placementService.deletePlacement(id);
        return ResponseEntity.noContent().build();
    }
}
