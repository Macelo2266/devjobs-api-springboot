package br.com.devjobs.api.devjobs_api.service;


import br.com.devjobs.api.devjobs_api.dto.UserResponseDTO;
import br.com.devjobs.api.devjobs_api.models.User;
import br.com.devjobs.api.devjobs_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponseDTO execute(User user) {
        this.userRepository
                .findByUsernameOrEmail(user.getUsername(), user.getEmail())
                .ifPresent((u) -> {
                    throw new Error("Usuário ou e-mail já existe");
                });

        var encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        var savedUser = this.userRepository.save(user);

        // Converte a entidade salva para o DTO de resposta
        return new UserResponseDTO(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
    }

    public List<UserResponseDTO> getAllUsers() {
        return this.userRepository.findAll()
                .stream() // Transforma a lista em um fluxo de dados
                .map(user -> new UserResponseDTO( // Mapeia cada User para um UserResponseDTO
                        user.getId(),
                        user.getName(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRole()
                ))
                .collect(Collectors.toList());
    }

}