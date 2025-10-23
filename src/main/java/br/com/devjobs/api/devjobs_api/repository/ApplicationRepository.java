package br.com.devjobs.api.devjobs_api.repository;

import br.com.devjobs.api.devjobs_api.models.Application;
import br.com.devjobs.api.devjobs_api.models.Job;
import br.com.devjobs.api.devjobs_api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {
    boolean existsByCandidateAndJob(User candidate, Job job);
    List<Application> findAllByJob(Job job);
}
