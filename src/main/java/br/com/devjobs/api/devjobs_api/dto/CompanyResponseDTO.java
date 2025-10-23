package br.com.devjobs.api.devjobs_api.dto;


import java.time.LocalDateTime;
import java.util.UUID;

public record CompanyResponseDTO(
        UUID id,
        String name,
        String website,
        String cnpj,
        String description,
        LocalDateTime createdAt
) { }
