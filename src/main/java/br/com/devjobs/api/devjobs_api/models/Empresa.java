package br.com.devjobs.api.devjobs_api.models;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity(name = "empresas")
@Data
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;
    private String setor;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "empresa")
    private List<Recrutador> recrutadores;
}
