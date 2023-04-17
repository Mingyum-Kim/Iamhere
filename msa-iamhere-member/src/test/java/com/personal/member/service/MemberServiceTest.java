package com.personal.member.service;

import com.personal.member.config.AppConfig;
import com.personal.member.domain.Member;
import com.personal.member.dto.MemberDTO;
import com.personal.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

import static org.assertj.core.api.BDDAssumptions.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {
    private static final String MAIL = "test@email.com";
    private static final String PASSWORD = "12345";
    private static final Date BIRTH = new Date(2021 - 05 - 05);

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Test
    @DisplayName("비밀번호가 암호화되어서 DB에 저장된다.")
    void insertMember() throws Exception {
        // given
        MemberDTO memberDTO = createMemberDTO();
        // when
        Long memberId = memberService.insertMember(memberDTO);
        // then
        assertNotNull(memberId);
        assertTrue(memberService.matchPassword(PASSWORD, memberService.findById(memberId).get().getPassword()));
    }

    private MemberDTO createMemberDTO() {
        return MemberDTO.builder()
                .mail(MAIL)
                .password(PASSWORD)
                .birth(BIRTH)
                .build();
    }
}