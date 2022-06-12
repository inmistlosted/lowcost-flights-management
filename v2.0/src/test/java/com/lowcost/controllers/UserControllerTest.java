package com.lowcost.controllers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("qwe")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/del-tickets.sql","/create-seat-after.sql", "/create-user-before.sql", "/flights-list-before.sql", "/create-seat-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/del-tickets.sql", "/create-seat-after.sql", "/create-user-after.sql", "/flights-list-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void showCabinet() throws Exception {
        this.mockMvc.perform(get("/cabinet"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='cab_user']").string("qwe"))
                .andExpect(xpath("//*[@id='cab_phone']").string("123456"));
    }

    @Test
    void showReplenishForm() throws Exception {
        this.mockMvc.perform(get("/replenish"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='rep_ttl']").string("Enter amount of money:"));
    }

    @Test
    void replenish() throws Exception {
        MockHttpServletRequestBuilder multipart = multipart("/replenish")
                .param("money", "10")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='cab_acc']").string("$20.0"));
    }
}