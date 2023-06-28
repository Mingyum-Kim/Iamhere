package com.personal.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.member.dto.LoginDTO;
import com.personal.member.dto.MemberDTO;
import com.personal.member.exception.AppException;
import com.personal.member.exception.ErrorCode;
import com.personal.member.service.MailService;
import com.personal.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
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

    @MockBean
    private HttpServletRequest request;

    @MockBean
    private HttpServletResponse response;

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
    void join() throws Exception {
        // given

        MemberDTO memberDTO = new MemberDTO("gms08194@gmail.com", "asdf1234", new Date(2023 - 01 - 02));
        when(memberService.join(any(MemberDTO.class))).thenReturn(memberDTO.toEntity());

        // when
        mockMvc.perform(post("/api/v1/members/join")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberDTO))
                        .sessionAttr("mailVerified", true))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(memberDTO.toEntity())));
    }

    @DisplayName("이메일 인증을 완료하지 않아 회원가입에 실패한다.")
    @Test
    void joinWithoutVerification() throws Exception {
        // given
        MemberDTO memberDTO = new MemberDTO("gms08194@gmail.com", "asdf1234", new Date(2023 - 01 - 02));
        when(memberService.join(any(MemberDTO.class))).thenReturn(memberDTO.toEntity());

        // when
        mockMvc.perform(post("/api/v1/members/join")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberDTO)))
                .andExpect(status().isForbidden());
    }


    @DisplayName("이메일 중복으로 회원가입에 실패한다.")
    @Test
    public void insertMember_fail() throws Exception {
        // given
        MemberDTO memberDTO = new MemberDTO("gms08194@gmail.com", "asdf1234", new Date(2023 - 01 - 02));
        when(memberService.join(any(MemberDTO.class))).thenThrow(new RuntimeException("he mail is already exist!"));

        // when
        mockMvc.perform(post("/api/v1/members/join")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberDTO))
                        .sessionAttr("mailVerified", true))
                .andExpect(status().isConflict());
    }

    @DisplayName("인증메일을 정상적으로 발송한다.")
    @Test
    void sendMail() throws Exception {
        String to = "mingyum119@gmail.com";
        String key = "3pqb9qKs";
        when(mailService.sendSimpleMessage(anyString())).thenReturn(key);

         mockMvc.perform(post("/api/v1/members/join/confirm")
                        .with(csrf())
                        .param("mail", to))
                .andExpect(status().isOk())
                .andExpect(content().string(key));
    }

    @DisplayName("로그인에 성공한다.")
    @Test
    @WithAnonymousUser
    public void login_success() throws Exception {
        String mail = "asdf1221@naver.com";
        String password = "asdf1234";
        String token = "token";
        LoginDTO loginDTO = new LoginDTO(mail, password);
        when(memberService.login(loginDTO)).thenReturn(token);

        // when
        mockMvc.perform(post("/api/v1/members/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string(token));
    }

    @DisplayName("Id가 존재하지 않아 로그인에 실패한다.")
    @Test
    @WithAnonymousUser
    public void login_notfound() throws Exception{
        String mail = "asdf1221@naver.com";
        String password = "asdf1234";
        LoginDTO loginDTO = new LoginDTO(mail, password);
        when(memberService.login(loginDTO)).thenThrow(new AppException(ErrorCode.MEMBER_NOT_FOUND));

        mockMvc.perform(post("/api/v1/members/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isNotFound());
    }

    @DisplayName("password가 일치하지 않아 로그인에 실패한다.")
    @Test
    @WithAnonymousUser
    public void login_unauthorized() throws Exception{
        String mail = "asdf1221@naver.com";
        String password = "asdf1234";
        LoginDTO loginDTO = new LoginDTO(mail, password);
        when(memberService.login(loginDTO)).thenThrow(new AppException(ErrorCode.INVALID_PASSWORD));

        mockMvc.perform(post("/api/v1/members/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isUnauthorized());
    }

}