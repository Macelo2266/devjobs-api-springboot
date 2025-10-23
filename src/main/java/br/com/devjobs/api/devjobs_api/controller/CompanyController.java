package br.com.devjobs.api.devjobs_api.controller;

import br.com.devjobs.api.devjobs_api.dto.CompanyDTO;
import br.com.devjobs.api.devjobs_api.dto.CompanyResponseDTO;
import br.com.devjobs.api.devjobs_api.models.Company;
import br.com.devjobs.api.devjobs_api.repository.CompanyRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @PostMapping("/")
    public ResponseEntity<Company> createCompany(@RequestBody @Valid CompanyDTO companyDTO) {
        Company company = new Company();
        company.setName(companyDTO.name());
        company.setWebsite(companyDTO.website());
        company.setCnpj(companyDTO.cnpj());
        company.setDescription(companyDTO.description());

        Company savedCompany = companyRepository.save(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCompany);
    }



    @GetMapping("/")
    public ResponseEntity<List<CompanyResponseDTO>> listCompanies() {
        List<CompanyResponseDTO> companies = companyRepository.findAll()
                .stream()
                .map(c -> new CompanyResponseDTO(
                        c.getId(),
                        c.getName(),
                        c.getWebsite(),
                        c.getCnpj(),
                        c.getDescription(),
                        c.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(companies);
    }
}


