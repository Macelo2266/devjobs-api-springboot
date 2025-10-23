package br.com.devjobs.api.devjobs_api.controller;

import br.com.devjobs.api.devjobs_api.dto.ApplicationResponseDTO;
import br.com.devjobs.api.devjobs_api.dto.JobCreateDTO;
import br.com.devjobs.api.devjobs_api.dto.JobResponseDTO;
import br.com.devjobs.api.devjobs_api.models.Application;
import br.com.devjobs.api.devjobs_api.models.User;
import br.com.devjobs.api.devjobs_api.service.ApplicationService;
import br.com.devjobs.api.devjobs_api.service.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private ApplicationService applicationService;


    @PostMapping("/")
    public ResponseEntity<JobResponseDTO> createJob(
            @Valid @RequestBody JobCreateDTO jobCreateDTO,
            Authentication authentication) {
        User recruiter = (User) authentication.getPrincipal();
        JobResponseDTO createdJob = jobService.createJob(jobCreateDTO, recruiter);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdJob);
    }

    @GetMapping("/")
    public ResponseEntity<List<JobResponseDTO>> listAllJobs() {
        List<JobResponseDTO> jobs = jobService.listAllJobs();
        return ResponseEntity.ok(jobs);
    }

    @PostMapping("/{jobId}/apply")
    public ResponseEntity<?> apply(@PathVariable UUID jobId, Authentication authentication) {
        User candidate = (User) authentication.getPrincipal();
        try {
            ApplicationResponseDTO newApplication = this.applicationService.applyToJob(jobId, candidate.getId());
            return ResponseEntity.ok(newApplication);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{jobId}/applications")
    public ResponseEntity<?> listApplicationsForJob(@PathVariable UUID jobId, Authentication authentication) {
        User recruiter = (User) authentication.getPrincipal();
        try {
            List<ApplicationResponseDTO> applications = applicationService.findApplicationsByJob(jobId, recruiter);
            return ResponseEntity.ok(applications);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }

    }

}