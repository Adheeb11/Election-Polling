package com.ty;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final ElectionService electionService;

    public UserController(ElectionService electionService) {
        this.electionService = electionService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/user/vote")
    public String showVotePage(Model model, Authentication authentication) {
        electionService.initCandidates();

        String username = authentication.getName();
        boolean hasVoted = electionService.hasUserVoted(username);
        Candidate votedCandidate = electionService.getUserVotedCandidate(username);

        model.addAttribute("candidates", electionService.getAllCandidates());
        model.addAttribute("hasVoted", hasVoted);
        model.addAttribute("votedCandidate", votedCandidate);

        return "vote";
    }

    @PostMapping("/user/vote")
    public String castVote(@RequestParam("candidateId") Long candidateId,
                           Model model,
                           Authentication authentication) {

        String username = authentication.getName();
        boolean success = electionService.voteForUser(username, candidateId);

        if (success) {
            model.addAttribute("message", "Your vote has been cast successfully!");
        } else {
            model.addAttribute("message", "You have already voted! Multiple votes are not allowed.");
        }

        return "vote-success";
    }
}
