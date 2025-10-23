package br.com.devjobs.api.devjobs_api.controller;

import br.com.devjobs.api.devjobs_api.models.Company;
import br.com.devjobs.api.devjobs_api.models.User;
import br.com.devjobs.api.devjobs_api.repository.CompanyRepository;
import br.com.devjobs.api.devjobs_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/recruiters")
public class RecruiterController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @PutMapping("/{userId}/company/{companyId}")
    public ResponseEntity<String> associateCompany(
            @PathVariable UUID userId,
            @PathVariable UUID companyId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        user.setCompany(company);
        userRepository.save(user);

        return ResponseEntity.ok("Recrutador vinculado à empresa com sucesso!");
    }

}

