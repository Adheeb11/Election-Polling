package com.ty;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    private final ElectionService electionService;

    public AdminController(ElectionService electionService) {
        this.electionService = electionService;
    }

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        var candidates = electionService.getAllCandidates();
        int totalVotes = electionService.getTotalVotes();
        Candidate leader = electionService.getLeadingCandidate();

        model.addAttribute("candidates", candidates);
        model.addAttribute("totalVotes", totalVotes);
        model.addAttribute("leader", leader);

        return "admin-dashboard";
    }
}
