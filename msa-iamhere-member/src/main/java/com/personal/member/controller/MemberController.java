package com.personal.member.controller;

import com.personal.member.domain.Member;
import com.personal.member.dto.LoginDTO;
import com.personal.member.dto.MemberDTO;
import com.personal.member.service.MailService;
import com.personal.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> confirmMail(HttpServletResponse response, @RequestParam("mail") String mail) throws Exception {
        String code = mailService.sendSimpleMessage(mail);
        Cookie verificationCookie = new Cookie("verificationCode", code);
        verificationCookie.setMaxAge(180);
        response.addCookie(verificationCookie);
        return ResponseEntity.ok(code);
    }

    @PostMapping("/join/verify")
    public ResponseEntity<Boolean> verifyMail(HttpServletRequest request,
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

    @GetMapping("/get-current-member")
    public Long getCurrentMember(Authentication authentication){
        log.info("authentication.getName() : " + authentication.getName());
        Member member = memberService.getMember(authentication.getName());
        return member.getId();
    }

    @GetMapping("/hello-world")
    public String test(){
        return "Hello, world!";
    }

}
