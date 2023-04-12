package com.personal.member.controller;

import com.personal.member.dto.MemberDTO;
import com.personal.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<Long> insertMember(@RequestBody MemberDTO memberDTO) throws Exception {
        return ResponseEntity.ok(memberService.insertMember(memberDTO));
    }
}
