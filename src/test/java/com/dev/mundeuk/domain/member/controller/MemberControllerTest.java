package com.dev.mundeuk.domain.member.controller;

import com.dev.mundeuk.domain.member.dto.MemberDto;
import com.dev.mundeuk.domain.member.entity.Member;
import com.dev.mundeuk.domain.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberControllerTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember(String id, String password) {
        MemberDto memberDto = new MemberDto();
        memberDto.setId(id);
        memberDto.setPassword(password);
        memberDto.setName("홍길동");
        Member member = Member.toEntity(memberDto, passwordEncoder);
        return memberService.join(member);
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginSuccess() throws Exception {
        String id = "test@test.com";
        String password = "test1234!@#";
        Member member = this.createMember(id, password);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/member/login")
                .param("id", id)
                .param("password", password)
                );

        resultActions.andExpect(SecurityMockMvcResultMatchers.authenticated());
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    public void loginFailure() throws Exception {
        String id = "test@test.com";
        String password = "test1234!@#";
        this.createMember(id, password);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/member/login")
                .param("id", id)
                .param("password", "test1234!@"));

        resultActions.andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }
}