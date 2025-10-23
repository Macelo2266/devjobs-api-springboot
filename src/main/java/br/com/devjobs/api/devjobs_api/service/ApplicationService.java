package br.com.devjobs.api.devjobs_api.service;

import br.com.devjobs.api.devjobs_api.dto.ApplicationResponseDTO;
import br.com.devjobs.api.devjobs_api.models.Application;
import br.com.devjobs.api.devjobs_api.models.User;
import br.com.devjobs.api.devjobs_api.repository.ApplicationRepository;
import br.com.devjobs.api.devjobs_api.repository.JobRepository;
import br.com.devjobs.api.devjobs_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    public ApplicationResponseDTO applyToJob(UUID jobId, UUID candidateId) {
        var candidate = userRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidato não encontrado."));

        var job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Vaga não encontrada."));

        if (applicationRepository.existsByCandidateAndJob(candidate, job)) {
            throw new RuntimeException("Você já se candidatou para esta vaga.");
        }

        Application newApplication = new Application();
        newApplication.setCandidate(candidate);
        newApplication.setJob(job);
        newApplication.setStatus("APPLIED");

        var savedApplication = this.applicationRepository.save(newApplication);

        // Retorna o DTO em vez da entidade completa, para manter a consistência
        return new ApplicationResponseDTO(savedApplication);
    }

    // Método movido para o local correto (fora do outro método)
    public List<ApplicationResponseDTO> findApplicationsByJob(UUID jobId, User recruiter) {
        // 1. Busca a vaga pelo ID
        var job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Vaga não encontrada."));

        // 2. VERIFICAÇÃO DE PERMISSÃO: o recrutador logado pertence à mesma empresa da vaga?
        if (recruiter.getCompany() == null || !job.getCompany().getId().equals(recruiter.getCompany().getId())) {
            throw new SecurityException("Você não tem permissão para acessar os candidatos desta vaga.");
        }

        // 3. Busca as candidaturas
        var applications = applicationRepository.findAllByJob(job);

        // 4. Converte a lista de Application para uma lista de ApplicationResponseDTO
        return applications.stream()
                .map(ApplicationResponseDTO::new)
                .toList();
    }
}