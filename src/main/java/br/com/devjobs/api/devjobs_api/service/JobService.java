package br.com.devjobs.api.devjobs_api.service;


import br.com.devjobs.api.devjobs_api.dto.JobCreateDTO;
import br.com.devjobs.api.devjobs_api.dto.JobResponseDTO;
import br.com.devjobs.api.devjobs_api.models.Job;
import br.com.devjobs.api.devjobs_api.models.User;
import br.com.devjobs.api.devjobs_api.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;


    public JobResponseDTO createJob(JobCreateDTO jobCreateDTO, User recruiter) {
        if (recruiter.getCompany() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Você precisa estar vinculado a uma empresa para criar uma vaga."
            );
        }

        Job newJob = new Job();
        newJob.setTitle(jobCreateDTO.title());
        newJob.setDescription(jobCreateDTO.description());
        newJob.setLevel(jobCreateDTO.level());
        newJob.setStatus("ATIVO");
        newJob.setCompany(recruiter.getCompany());

        var savedJob = jobRepository.save(newJob);

        // Converte a entidade salva para o DTO de resposta antes de retornar
        return new JobResponseDTO(savedJob);
    }

    // Método agora retorna uma lista de JobResponseDTO
    public List<JobResponseDTO> listAllJobs() {
        return jobRepository.findAll()
                .stream()
                .map(JobResponseDTO::new) // Converte cada Job da lista para um DTO
                .toList();
    }
}

