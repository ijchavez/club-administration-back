package uy.com.club.administration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.com.club.administration.domain.Member;
import uy.com.club.administration.dto.request.MemberDTO;
import uy.com.club.administration.dto.request.MemberListDTO;
import uy.com.club.administration.services.MemberService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public ResponseEntity<Page<MemberDTO>> findMember(@RequestBody MemberDTO memberDTO, Pageable pageable) {
        return ResponseEntity.ok(memberService.findMemberByQuery(memberDTO, pageable));
    }

    @PostMapping("/create")
    public ResponseEntity<MemberDTO> createMember(@RequestBody MemberDTO memberDTO) {
        return ResponseEntity.ok(memberService.createMember(memberDTO));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Member> deleteMemberById(@PathVariable String id) {
        return ResponseEntity.ok(memberService.deleteMember(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberDTO> updateMember(@PathVariable String id, @RequestBody MemberDTO memberDTO) {
        memberService.updateMember(id, memberDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable String id) {
        return ResponseEntity.ok(memberService.getMember(id));
    }

    @GetMapping()
    public ResponseEntity<List<MemberDTO>> getAllMember() {
        return ResponseEntity.ok(memberService.getAllMember());
    }

    @PostMapping("/join")
    public ResponseEntity<MemberDTO> joinMemberToPartner(@RequestBody MemberDTO memberDTO) {
        return ResponseEntity.ok(memberService.joinMemberToPartner(memberDTO));
    }
}