package com.weather.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class ViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testStreamTony() throws Exception {
        mockMvc.perform(get("/tony"))
                .andExpect(status().isOk())  // Assert that HTTP status is 200 OK
                .andExpect(view().name("tony"));  // Assert that the view is "tony"
    }
}