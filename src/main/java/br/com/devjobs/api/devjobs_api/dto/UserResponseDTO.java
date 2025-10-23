package br.com.devjobs.api.devjobs_api.dto;

import java.util.UUID;

public record UserResponseDTO(
                              UUID id,
                              String name,
                              String username,
                              String email,
                              String role) {
}
