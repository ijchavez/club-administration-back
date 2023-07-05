package uy.com.club.administration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.com.club.administration.domain.Contribution;
import uy.com.club.administration.dto.request.ContributionDTO;
import uy.com.club.administration.dto.request.ContributionListDTO;
import uy.com.club.administration.mappers.ContributionMapper;
import uy.com.club.administration.reports.ContributionReport;
import uy.com.club.administration.services.ContributionService;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/contribution")
@RequiredArgsConstructor
public class ContributionController {
    private final ContributionService contributionService;

    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    Page<ContributionDTO> contributionPageable(Pageable pageable) {
        return contributionService.findAll(pageable);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Page<ContributionDTO>> findContributionPageable(@RequestBody ContributionDTO contributionDTO, Pageable pageable) {
        return ResponseEntity.ok(contributionService.findContributionByQuery(contributionDTO, pageable));
    }

    @PostMapping("/create")
    public ResponseEntity<ContributionDTO> createContribution(@RequestBody ContributionDTO contributionDTO) throws ParseException {
        contributionService.createContribution(contributionDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContributionDTO> updateContribution(@PathVariable String id, @RequestBody ContributionDTO contributionDTO) {
        return ResponseEntity.ok(contributionService.updateContribution(id, contributionDTO));
    }

    @GetMapping("/{month}/{year}")
    public ResponseEntity<List<Contribution>> getContributionById(@PathVariable int year, @PathVariable int month) {
        return ResponseEntity.ok(contributionService.getContributionMonth(year, month));
    }

    @GetMapping("/partner/{id}")
    public ResponseEntity<List<ContributionDTO>> getContributionByPartnerId(@PathVariable String id) {
        return ResponseEntity.ok(contributionService.getContributionByPartnerId(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<ContributionDTO>> getAllContribution() {
        return ResponseEntity.ok(contributionService.getAllContribution());
    }

    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=contributions_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<ContributionListDTO> listContribution = contributionService.listAll();

        ContributionReport excelExporter = new ContributionReport(listContribution);

        excelExporter.export(response);
    }

    @GetMapping("/export/excel/{month}/{year}")
    public void exportToExcelFromDate(HttpServletResponse response, @PathVariable int year, @PathVariable int month) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=contributions_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<ContributionListDTO> listContribution = ContributionMapper.INSTANCE.mapContributionToList(contributionService.getContributionMonth(year, month));

        ContributionReport excelExporter = new ContributionReport(listContribution);

        excelExporter.export(response);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteContributionById(@PathVariable String id) {
        contributionService.deleteContribution(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContributionDTO> getAllContribution(@PathVariable String id) {
        return ResponseEntity.ok(contributionService.getContribution(id));
    }

    @GetMapping("/filters/{payMethod}/{parentId}/{date}")
    public ResponseEntity<List<Contribution>> getContributionByPartnerIds(@PathVariable String payMethod, @PathVariable String parentId, @PathVariable String date) throws ParseException {
        return ResponseEntity.ok(contributionService.findContributionsByFilters(payMethod, parentId, date));
    }
}