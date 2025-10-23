package br.com.devjobs.api.devjobs_api.dto;

import jakarta.validation.constraints.NotBlank;

public record JobCreateDTO(
        @NotBlank(message = "O título é obrigatório")
        String title,

        @NotBlank(message = "A descrição é obrigatória")
        String description,

        @NotBlank(message = "O nível é obrigatório")
        String level
) {}
