package com.personal.member.service;

import com.personal.member.domain.Member;
import com.personal.member.dto.MemberDTO;
import com.personal.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class MemberServiceTest {
    private static final String MAIL = "test@email.com";
    private static final String PASSWORD = "12345";
    private static final Date BIRTH = new Date(2021 - 05 - 05);

    @Autowired
    private MemberService memberService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    @DisplayName("비밀번호가 암호화되어서 DB에 저장된다.")
    void insertMember() throws Exception {
        // given
        MemberDTO memberDTO = createMemberDTO();
        String encoded = "newEncodedPassword";
        memberDTO.setPassword(encoded);
        when(memberRepository.save(any(Member.class))).thenReturn(memberDTO.toEntity());
        when(bCryptPasswordEncoder.encode(any(String.class))).thenReturn(encoded);
        when(memberRepository.findByMail(any(String.class))).thenReturn(Optional.empty());

        // when
        Member member =  memberService.join(memberDTO);

        // then
        assertEquals(member.getPassword(), encoded);
    }

    private MemberDTO createMemberDTO() {
        return MemberDTO.builder()
                .mail(MAIL)
                .password(PASSWORD)
                .birth(BIRTH)
                .build();
    }
}