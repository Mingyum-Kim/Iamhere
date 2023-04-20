package com.personal.member.controller;

import com.personal.member.dto.MemberDTO;
import com.personal.member.service.MailService;
import com.personal.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    private final MailService mailService;

    @GetMapping("/")
    public String home(){
        return "Hello, world!";
    }

    @PostMapping("/join")
    public ResponseEntity<Long> insertMember(@RequestBody MemberDTO memberDTO) throws Exception {
        return ResponseEntity.ok(memberService.insertMember(memberDTO));
    }

    @PostMapping("/join/confirm")
    @ResponseBody
    public ResponseEntity<String> confirmMail(@RequestParam("mail") String mail) throws Exception {
        String code = mailService.sendSimpleMessage(mail);
        return ResponseEntity.ok(code);
    }
}
