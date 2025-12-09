package com.ty;

import jakarta.persistence.*;

@Entity
public class Voter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;   // security username

    @ManyToOne
    private Candidate candidate; // whom they voted for

    public Voter() {
    }

    public Voter(String username, Candidate candidate) {
        this.username = username;
        this.candidate = candidate;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
}
