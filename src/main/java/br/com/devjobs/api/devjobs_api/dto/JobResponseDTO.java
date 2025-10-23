package br.com.devjobs.api.devjobs_api.dto;

import br.com.devjobs.api.devjobs_api.models.Job;

import java.util.UUID;

public record JobResponseDTO(UUID id,
                             String title,
                             String description,
                             String level,
                             String status,
                             String companyName) {

    public JobResponseDTO(Job job) {
        this(
                job.getId(),
                job.getTitle(),
                job.getDescription(),
                job.getLevel(),
                job.getStatus(),
                job.getCompany().getName() // Pegamos apenas o nome da empresa
        );
    }
}
