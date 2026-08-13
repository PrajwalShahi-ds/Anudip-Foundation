package com.placement.management.service;

import com.placement.management.dto.PlacementDTO;
import com.placement.management.dto.PlacementRequestDTO;

import java.util.List;

public interface PlacementService {
    PlacementDTO createPlacement(PlacementRequestDTO requestDTO);
    PlacementDTO getPlacementById(Long id);
    PlacementDTO getPlacementByStudentId(Long studentId);
    List<PlacementDTO> getAllPlacements();
    List<PlacementDTO> getPlacementsByCompanyId(Long companyId);
    void deletePlacement(Long id);
}
