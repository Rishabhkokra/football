package com.assignment.football.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.assignment.football.service.FootballService;

@SpringBootTest
@AutoConfigureMockMvc
public class FootBallControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	FootballService footBallService;

	@Test
	public void apiTest() throws Exception {
		Mockito.when(footBallService.getInfo(Mockito.any())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
		this.mockMvc.perform(get("/api/v1/info/football").param("action", "get_countries")).andExpect(status().isOk());
	}
	
	@Test
	public void apiTestException() throws Exception {
		Mockito.when(footBallService.getInfo(Mockito.any())).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
		this.mockMvc.perform(get("/api/v1/info/football")).andExpect(status().isBadRequest());
	}
}