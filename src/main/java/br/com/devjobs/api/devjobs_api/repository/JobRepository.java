package br.com.devjobs.api.devjobs_api.repository;

import br.com.devjobs.api.devjobs_api.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {
}
