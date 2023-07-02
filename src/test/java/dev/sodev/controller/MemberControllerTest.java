package dev.sodev.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sodev.global.config.SecurityConfig;
import dev.sodev.controller.request.MemberJoinRequest;
import dev.sodev.controller.response.MemberJoinResponse;
import dev.sodev.global.exception.ErrorCode;
import dev.sodev.global.exception.SodevApplicationException;
import dev.sodev.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@Import({SecurityConfig.class})
@WebMvcTest(MemberController.class)
public class MemberControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean MemberService memberService;

    @Test
    @DisplayName("회원가입 성공")
    @WithAnonymousUser
    public void 회원가입() throws Exception {

        MemberJoinRequest memberJoinRequest = MemberJoinRequest.builder()
                .email("a@naver.com")
                .password("1234!Qwerty")
                .phone("010-1111-1111")
                .nickName("TEST")
                .build();

        when(memberService.join(memberJoinRequest)).thenReturn(mock(MemberJoinResponse.class));

        mockMvc.perform(post("/v1/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(memberJoinRequest))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("아이디 중복되어 회원가입 실패")
    @WithAnonymousUser
    public void 아이디_중복_회원가입_실패() throws Exception {

        MemberJoinRequest memberJoinRequest = MemberJoinRequest.builder()
                .email("a@naver.com")
                .password("1234!Qwerty")
                .phone("010-1111-1111")
                .nickName("TEST")
                .build();

        when(memberService.join(memberJoinRequest)).thenThrow(new SodevApplicationException(ErrorCode.DUPLICATE_USER_ID));

        mockMvc.perform(post("/v1/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(memberJoinRequest))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is(ErrorCode.DUPLICATE_USER_ID.getStatus().value()));
    }
    @Test
    @DisplayName("유효성 검사 성공")
    @WithAnonymousUser
    public void 유효성_검사_성공() throws Exception {

        MemberJoinRequest memberJoinRequest = MemberJoinRequest.builder()
                .email("a@naver.com")
                .password("1234!Qwerty")
                .phone("010-1111-1111")
                .nickName("TEST")
                .build();


        mockMvc.perform(post("/v1/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(memberJoinRequest))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("유효성 검사 실패")
    @WithAnonymousUser
    public void 유효성_검사_실패() throws Exception {

        MemberJoinRequest memberJoinRequest = MemberJoinRequest.builder()
                .email("a")
                .password("1234")
                .phone("010")
                .nickName(null)
                .build();


        mockMvc.perform(post("/v1/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(memberJoinRequest))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}