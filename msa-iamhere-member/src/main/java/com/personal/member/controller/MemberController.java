package com.personal.member.controller;

import com.personal.member.dto.MemberDTO;
import com.personal.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<Long> insertMember(@RequestBody MemberDTO memberDTO) {
        return ResponseEntity.ok(memberService.insertMember(memberDTO));
    }
}
