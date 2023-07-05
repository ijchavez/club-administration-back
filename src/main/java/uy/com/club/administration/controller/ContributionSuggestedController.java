package uy.com.club.administration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.com.club.administration.domain.ContributionSuggested;
import uy.com.club.administration.dto.request.ContributionSuggestedDTO;
import uy.com.club.administration.services.ContributionSuggestedService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/contribution-suggested")
@RequiredArgsConstructor
public class ContributionSuggestedController {
    private final ContributionSuggestedService contributionSuggestedService;

    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    Page<ContributionSuggested> filterContributionSuggested(Pageable pageable) {
        return contributionSuggestedService.findAll(pageable);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Page<ContributionSuggestedDTO>> findContributionSuggested(@RequestBody ContributionSuggestedDTO contributionSuDTO, Pageable pageable) {
        return ResponseEntity.ok(contributionSuggestedService.findMemberByQuery(contributionSuDTO, pageable));
    }

    @PostMapping("/create")
    public ResponseEntity<ContributionSuggestedDTO> createContributionSuggested(@RequestBody ContributionSuggestedDTO contributionSuggestedDTO) {

        return ResponseEntity.ok(contributionSuggestedService.createContributionSuggested(contributionSuggestedDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContributionSuggestedDTO> updateContributionSuggested(@PathVariable String id, @RequestBody ContributionSuggestedDTO contributionSuggestedDTO) {
        return ResponseEntity.ok(contributionSuggestedService.updateContributionSuggested(id, contributionSuggestedDTO));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<ContributionSuggested> deleteContributionSuggested(@PathVariable String id) {
        contributionSuggestedService.deleteContributionSuggested(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContributionSuggestedDTO> getContributionSuggestedById(@PathVariable String id) {
        return ResponseEntity.ok(contributionSuggestedService.getContributionSuggested(id));
    }

    @GetMapping()
    public ResponseEntity<List<ContributionSuggestedDTO>> getContributionSuggested() {
        return ResponseEntity.ok(contributionSuggestedService.getAllContributionsSuggested());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContributionSuggestedById(@PathVariable String id) {
        contributionSuggestedService.deleteContributionSuggested(id);
        return ResponseEntity.ok("deleted successful");
    }

    @GetMapping("/montoSuggested/{categoryId}/{paymentMethod}")
    public ResponseEntity<ContributionSuggestedDTO> searchContribution(@PathVariable String categoryId, @PathVariable String paymentMethod) {
        return ResponseEntity.ok(contributionSuggestedService.searchContribution(categoryId, paymentMethod));
    }
}