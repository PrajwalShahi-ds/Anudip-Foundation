package com.placement.management.service.impl;

import com.placement.management.dto.CompanyDTO;
import com.placement.management.entity.Company;
import com.placement.management.enums.CompanyStatus;
import com.placement.management.exception.DuplicateResourceException;
import com.placement.management.exception.ResourceNotFoundException;
import com.placement.management.repository.CompanyRepository;
import com.placement.management.service.CompanyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public CompanyDTO createCompany(CompanyDTO dto) {
        if (companyRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Company with email '" + dto.getEmail() + "' already registered");
        }

        Company company = mapDTOToEntity(dto, new Company());
        Company saved = companyRepository.save(company);
        return mapEntityToDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyDTO getCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with ID: " + id));
        return mapEntityToDTO(company);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyDTO> getAllCompanies() {
        return companyRepository.findAll()
                .stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompanyDTO> getAllCompaniesPaginated(Pageable pageable) {
        return companyRepository.findAll(pageable).map(this::mapEntityToDTO);
    }

    @Override
    public CompanyDTO updateCompany(Long id, CompanyDTO dto) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with ID: " + id));

        if (companyRepository.existsByEmailAndIdNot(dto.getEmail(), id)) {
            throw new DuplicateResourceException("Company with email '" + dto.getEmail() + "' already exists");
        }

        mapDTOToEntity(dto, company);
        Company updated = companyRepository.save(company);
        return mapEntityToDTO(updated);
    }

    @Override
    public CompanyDTO updateCompanyStatus(Long id, CompanyStatus status) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with ID: " + id));
        company.setStatus(status);
        Company saved = companyRepository.save(company);
        return mapEntityToDTO(saved);
    }

    @Override
    public void deleteCompany(Long id) {
        if (!companyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Company not found with ID: " + id);
        }
        companyRepository.deleteById(id);
    }

    private Company mapDTOToEntity(CompanyDTO dto, Company entity) {
        entity.setName(dto.getName());
        entity.setIndustry(dto.getIndustry());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setWebsite(dto.getWebsite());
        entity.setAddress(dto.getAddress());
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
        return entity;
    }

    public CompanyDTO mapEntityToDTO(Company entity) {
        return new CompanyDTO(
                entity.getId(),
                entity.getName(),
                entity.getIndustry(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getWebsite(),
                entity.getAddress(),
                entity.getStatus()
        );
    }
}
