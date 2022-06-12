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

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("qwe")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/flights-list-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql", "/flights-list-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class FlightControllerTest {
//    @Autowired
//    FlightController flightController;

    @Autowired
    MockMvc mockMvc;

    @Test
    void mainPageTest() throws Exception{
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div[1]/a").string("My cabinet"));
    }

    @Test
    void showFlightsTest() throws Exception{
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(xpath("/html/body/div[3]/div").nodeCount(2));
    }

    @Test
    void showFlight() throws Exception {
        this.mockMvc.perform(get("/flights").param("flightId", "1"))
                .andDo(print())
                .andExpect(xpath("//*[@id='fldir']").string("to Lwiw"))
                .andExpect(xpath("//*[@id='fldep']").string("12.12.2020 - 16:30"));
    }
}