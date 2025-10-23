package br.com.devjobs.api.devjobs_api.repository;

import br.com.devjobs.api.devjobs_api.models.Recrutador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RecrutadorRepository extends JpaRepository<Recrutador, UUID> {
}
