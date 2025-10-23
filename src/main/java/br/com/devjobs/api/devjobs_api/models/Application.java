package br.com.devjobs.api.devjobs_api.models;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "applications", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"candidate_id", "job_id"})
})
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String resumePath;
    private String status;

    @CreationTimestamp
    private LocalDateTime appliedAt;

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private User candidate;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

}
