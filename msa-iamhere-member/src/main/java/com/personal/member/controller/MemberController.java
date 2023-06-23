package com.personal.member.controller;

import com.personal.member.domain.Member;
import com.personal.member.dto.LoginDTO;
import com.personal.member.dto.MemberDTO;
import com.personal.member.service.MailService;
import com.personal.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    private final MailService mailService;

    private final HttpSession session;

    @PostMapping("/join")
    public ResponseEntity<Member> join(@RequestBody MemberDTO memberDTO) throws Exception {
        return ResponseEntity.ok(memberService.join(memberDTO));
    }

    @PostMapping("/join/confirm")
    @ResponseBody
    public ResponseEntity<String> confirmMail(@RequestParam("mail") String mail, HttpServletResponse response) throws Exception {
        String code = mailService.sendSimpleMessage(mail);
        Cookie verificationCookie = new Cookie("verificationCode", code);
        verificationCookie.setMaxAge(180);
        response.addCookie(verificationCookie);
        return ResponseEntity.ok(code);
    }

    @PostMapping("/verify")
    public ResponseEntity<Boolean> verifyMail(@RequestParam("verificationCode") String verificationCode,
                                              @RequestParam("mail") String mail,
                                              @CookieValue(value = "verificationCode") String storedVerificationCode) {
        boolean isVerified = false;
        if (verificationCode.equals(storedVerificationCode)){
            isVerified = true;
            session.setAttribute("mailVerified", true);
            session.setAttribute("userMail", mail);
        }
        return ResponseEntity.ok(isVerified);
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(memberService.login(loginDTO));
    }
}
