package br.com.devjobs.api.devjobs_api.dto;

import br.com.devjobs.api.devjobs_api.models.Application;

import java.time.LocalDateTime;
import java.util.UUID;

public record ApplicationResponseDTO(UUID id,
                                     String status,
                                     LocalDateTime appliedAt,
                                     CandidateInfoDTO candidate) {

    public ApplicationResponseDTO(Application application) {
        this(
                application.getId(),
                application.getStatus(),
                application.getAppliedAt(),
                new CandidateInfoDTO(application.getCandidate())
        );
    }
}
