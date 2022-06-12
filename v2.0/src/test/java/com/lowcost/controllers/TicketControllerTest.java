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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("qwe")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/del-tickets.sql","/create-seat-after.sql", "/create-user-before.sql", "/flights-list-before.sql", "/create-seat-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/del-tickets.sql", "/create-seat-after.sql", "/create-user-after.sql", "/flights-list-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class TicketControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void showTicketGui() throws Exception {
        this.mockMvc.perform(get("/buy").param("seatId", "1"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='ticketdir']/@value").string("to Lwiw"))
                .andExpect(xpath("//*[@id='seatnum']/@value").string("1"));
    }

    @Test
    void buyTicketFail() throws Exception {
        MockHttpServletRequestBuilder multipart = multipart("/buy")
                .param("seatId", "1")
                .param("userId", "1")
                .param("baggage", "Available")
                .param("priority", "Available")
                .param("price", "50")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='not_en_mon']").string("Not enough money on account. Click here to replenish"));
    }

    @Test
    void buyTicketSuccess() throws Exception {
        MockHttpServletRequestBuilder multipart = multipart("/buy")
                .param("seatId", "1")
                .param("userId", "2")
                .param("baggage", "Available")
                .param("priority", "Available")
                .param("price", "50")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(redirectedUrl("/"));
    }
}