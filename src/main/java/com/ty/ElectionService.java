package com.ty;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ElectionService {

    private final CandidateRepository candidateRepository;
    private final VoterRepository voterRepository;

    public ElectionService(CandidateRepository candidateRepository,
                           VoterRepository voterRepository) {
        this.candidateRepository = candidateRepository;
        this.voterRepository = voterRepository;
    }

    public void initCandidates() {
        if (candidateRepository.count() == 0) {
            candidateRepository.save(new Candidate("Candidate A", "Party A"));
            candidateRepository.save(new Candidate("Candidate B", "Party B"));
            candidateRepository.save(new Candidate("Candidate C", "Party C"));
            candidateRepository.save(new Candidate("Candidate D", "Party D"));
            candidateRepository.save(new Candidate("Candidate E", "Party E"));
        }
    }

    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    // Check if user already voted
    public boolean hasUserVoted(String username) {
        return voterRepository.existsByUsername(username);
    }

    // Get candidate user voted for
    public Candidate getUserVotedCandidate(String username) {
        return voterRepository.findByUsername(username)
                .map(Voter::getCandidate)
                .orElse(null);
    }

    // Vote only once per user
    public boolean voteForUser(String username, Long candidateId) {
        if (hasUserVoted(username)) {
            return false;
        }

        Optional<Candidate> optCandidate = candidateRepository.findById(candidateId);
        if (optCandidate.isEmpty()) {
            return false;
        }

        Candidate c = optCandidate.get();
        c.incrementVote();
        candidateRepository.save(c);

        Voter voter = new Voter(username, c);
        voterRepository.save(voter);

        return true;
    }

    public int getTotalVotes() {
        return candidateRepository.findAll()
                .stream()
                .mapToInt(Candidate::getVoteCount)
                .sum();
    }

    public Candidate getLeadingCandidate() {
        return candidateRepository.findAll()
                .stream()
                .max(Comparator.comparingInt(Candidate::getVoteCount))
                .orElse(null);
    }
}
