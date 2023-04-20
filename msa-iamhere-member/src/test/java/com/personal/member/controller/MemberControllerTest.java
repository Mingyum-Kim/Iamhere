package com.personal.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.member.dto.MemberDTO;
import com.personal.member.service.MailService;
import com.personal.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MemberController memberController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MailService mailService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @DisplayName("회원가입에 성공한다.")
    @Test
    void insertMember() throws Exception {
        // given
        MemberDTO memberDTO = new MemberDTO("gms08194@gmail.com", "asdf1234", new Date(2023 - 01 - 02));
        when(memberService.insertMember(any(MemberDTO.class))).thenReturn(1L);

        // when
        mockMvc.perform(post("/api/v1/members/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @DisplayName("이메일 중복으로 회원가입에 실패한다.")
    @Test
    public void insertMember_fail() throws Exception {
        // given
        MemberDTO memberDTO = new MemberDTO("gms08194@gmail.com", "asdf1234", new Date(2023 - 01 - 02));
        when(memberService.insertMember(any(MemberDTO.class))).thenThrow(new RuntimeException("he mail is already exist!"));

        // when
        mockMvc.perform(post("/api/v1/members/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberDTO)))
                .andExpect(status().isConflict());
    }

    @DisplayName("인증메일을 정상적으로 발송한다.")
    @Test
    void sendMail() throws Exception {
        String to = "gms08184@gmail.com";
        String key = "3pqb9qKf";
        when(mailService.sendSimpleMessage(to)).thenReturn(key);

        // when
        mockMvc.perform(post("/join/confirm")
                .param("mail", to))
                .andExpect(status().isOk())
                .andExpect(content().string(key));
    }

    @DisplayName("로그인에 성공한다.")
    @Test
    void login_success() throws Exception {

    }

    @DisplayName("Id가 존재하지 않아 로그인에 실패한다.")
    @Test
    void login_notfound() throws Exception{

    }

    @DisplayName("password가 일치하지 않아 로그인에 실패한다.")
    @Test
    void login_unauthorized() throws Exception{

    }

}