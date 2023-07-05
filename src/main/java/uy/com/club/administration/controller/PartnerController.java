package uy.com.club.administration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.com.club.administration.dto.request.PartnerCreateDTO;
import uy.com.club.administration.dto.request.PartnerDTO;
import uy.com.club.administration.dto.request.PartnerListDTO;
import uy.com.club.administration.services.PartnerService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/partner")
@RequiredArgsConstructor
public class PartnerController {
    private final PartnerService partnerService;

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    Page<PartnerListDTO> partnerPageable(@RequestBody PartnerDTO partnerDTO, Pageable pageable) {
        return partnerService.findAllPageable(partnerDTO, pageable);
    }

    @PostMapping("/create")
    public ResponseEntity<PartnerDTO> createPartner(@RequestBody PartnerCreateDTO partner) {
        return ResponseEntity.ok(partnerService.createPartner(partner));
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Page<PartnerDTO>> findPartner(@RequestBody PartnerDTO partnerDTO, Pageable pageable) {
        return ResponseEntity.ok(partnerService.findPartnerByQuery(partnerDTO, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePartner(@PathVariable String id, @RequestBody PartnerCreateDTO partner) {
        partnerService.updatePartner(id, partner);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartnerDTO> getPartnerById(@PathVariable String id) {
        return ResponseEntity.ok(partnerService.getPartner(id));
    }

    @GetMapping()
    public ResponseEntity<List<PartnerDTO>> getAllPartner() {
        return ResponseEntity.ok(partnerService.getAllPartner());
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deletePartnerById(@PathVariable String id) {
        partnerService.deletePartner(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/leave/{partnerId}/{memberId}")
    public ResponseEntity<PartnerDTO> leaveMemberFromPartner(@PathVariable String partnerId, @PathVariable String memberId) {
        return ResponseEntity.ok(partnerService.leaveMemberFromPartner(partnerId, memberId));
    }
}