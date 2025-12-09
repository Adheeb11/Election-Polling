package com.ty;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoterRepository extends JpaRepository<Voter, Long> {

    boolean existsByUsername(String username);

    Optional<Voter> findByUsername(String username);
}
