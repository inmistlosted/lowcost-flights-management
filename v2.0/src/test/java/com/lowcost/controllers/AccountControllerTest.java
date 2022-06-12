package com.lowcost.controllers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = {"/del-tickets.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/del-tickets.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class AccountControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void showRegistrationGui() throws Exception {
        this.mockMvc.perform(get("/registration"))
                .andDo(print())
                .andExpect(xpath("//*[@id='inppass']").string("Password:"));
    }

    @Test
    void registerUser() throws Exception {
        MockHttpServletRequestBuilder multipart = multipart("/registration")
                .param("username", "sasha")
                .param("password", "123")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(redirectedUrl("/login"));
    }
}