package br.com.devjobs.api.devjobs_api.dto;

import br.com.devjobs.api.devjobs_api.models.User;

import java.util.UUID;

public record CandidateInfoDTO(UUID id, String name, String email) {
    public CandidateInfoDTO(User user) {
        this(user.getId(), user.getName(), user.getEmail());
    }
}
