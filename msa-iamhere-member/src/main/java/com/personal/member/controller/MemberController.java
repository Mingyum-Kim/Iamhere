package com.personal.member.controller;

import com.personal.member.dto.LoginDTO;
import com.personal.member.dto.MemberDTO;
import com.personal.member.service.MailService;
import com.personal.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Slf4j
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    private final MailService mailService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody MemberDTO memberDTO, @SessionAttribute(name = "mailVerified", required = false) Boolean mailVerified) throws Exception {
        if(mailVerified == null || !mailVerified)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("이메일 인증이 완료되지 않았습니다.");
        return ResponseEntity.ok(memberService.join(memberDTO));
    }

    @PostMapping("/join/confirm")
    @ResponseBody
    public ResponseEntity<String> confirmMail(@RequestParam("mail") String mail,
                                              @RequestAttribute HttpServletResponse response) throws Exception {
        String code = mailService.sendSimpleMessage(mail);
        Cookie verificationCookie = new Cookie("verificationCode", code);
        verificationCookie.setMaxAge(180);
        response.addCookie(verificationCookie);
        return ResponseEntity.ok(code);
    }

    @PostMapping("/verify")
    public ResponseEntity<Boolean> verifyMail(@RequestAttribute HttpServletRequest request,
                                              @RequestParam("verificationCode") String verificationCode,
                                              @RequestParam("mail") String mail,
                                              @CookieValue(value = "verificationCode") String storedVerificationCode) {
        HttpSession session = request.getSession();
        boolean isVerified = false;
        if (verificationCode.equals(storedVerificationCode)) {
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

    @GetMapping("/nickname")
    @ResponseBody
    public ResponseEntity<String> getNickname(@RequestParam Long id) {
        System.out.println("getNickname 함수 실행");
        return ResponseEntity.ok(memberService.getNickname(id));
    }

}
