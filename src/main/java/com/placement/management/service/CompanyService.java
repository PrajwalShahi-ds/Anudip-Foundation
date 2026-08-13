package com.placement.management.service;

import com.placement.management.dto.CompanyDTO;
import com.placement.management.enums.CompanyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyService {
    CompanyDTO createCompany(CompanyDTO dto);
    CompanyDTO getCompanyById(Long id);
    List<CompanyDTO> getAllCompanies();
    Page<CompanyDTO> getAllCompaniesPaginated(Pageable pageable);
    CompanyDTO updateCompany(Long id, CompanyDTO dto);
    CompanyDTO updateCompanyStatus(Long id, CompanyStatus status);
    void deleteCompany(Long id);
}
