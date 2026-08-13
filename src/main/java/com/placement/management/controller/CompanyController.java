package com.placement.management.controller;

import com.placement.management.dto.CompanyDTO;
import com.placement.management.enums.CompanyStatus;
import com.placement.management.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin(origins = "*")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<CompanyDTO> createCompany(@Valid @RequestBody CompanyDTO companyDTO) {
        CompanyDTO createdCompany = companyService.createCompany(companyDTO);
        return new ResponseEntity<>(createdCompany, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable Long id) {
        CompanyDTO company = companyService.getCompanyById(id);
        return ResponseEntity.ok(company);
    }

    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
        List<CompanyDTO> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<CompanyDTO>> getAllCompaniesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return ResponseEntity.ok(companyService.getAllCompaniesPaginated(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDTO> updateCompany(
            @PathVariable Long id,
            @Valid @RequestBody CompanyDTO companyDTO) {
        CompanyDTO updated = companyService.updateCompany(id, companyDTO);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<CompanyDTO> updateCompanyStatus(
            @PathVariable Long id,
            @RequestParam CompanyStatus status) {
        CompanyDTO updated = companyService.updateCompanyStatus(id, status);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }
}
