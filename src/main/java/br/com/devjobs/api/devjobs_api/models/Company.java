package br.com.devjobs.api.devjobs_api.models;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity(name = "companies")
@Data
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String website;

    @Column(unique = true, length = 14)
    private String cnpj;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
