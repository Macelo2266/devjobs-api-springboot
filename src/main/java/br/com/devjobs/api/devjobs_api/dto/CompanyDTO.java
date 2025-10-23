package br.com.devjobs.api.devjobs_api.dto;

public record CompanyDTO(
        String name,
        String website,
        String cnpj,
        String description
) { }
