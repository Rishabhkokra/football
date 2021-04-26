package com.assignment.football.service;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import com.assignment.football.serviceImpl.FootballServiceInfoImpl;

@SpringBootTest
@AutoConfigureMockMvc
public class FootBallServiceTest {

	@InjectMocks
	private FootballService service = new FootballServiceInfoImpl();
	@Mock
	private RestTemplate temp;

	@Test
	public void apiTestNegative() throws Exception {
		HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
		ResponseEntity<?> resp = service.getInfo(req);
		assertEquals(resp.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void apiTest() throws Exception {
		String jsonResp = "{name: myname}";
		ResponseEntity<Object> response = new ResponseEntity<>(jsonResp, HttpStatus.OK);
		Mockito.when(temp.getForEntity(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(response);

		HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
		Map<String, String[]> map = new HashMap<>();
		map.put("action", new String[] { "get_country" });
		Mockito.when(req.getParameterMap()).thenReturn(map);

		ResponseEntity<?> resp = service.getInfo(req);
		assertNotNull(resp.getBody());
		assertEquals(resp.getBody(), jsonResp);
		assertEquals(resp.getStatusCodeValue(), 200);

	}

	@Test
	public void apiTestException() throws Exception {
		String jsonResp = "FAILURE HAPPENED :( !";
		ResponseEntity<Object> response = new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
		Mockito.when(temp.getForEntity(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(response);

		HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
		Map<String, String[]> map = new HashMap<>();
		map.put("action", new String[] { "get_country" });
		Mockito.when(req.getParameterMap()).thenReturn(map);

		ResponseEntity<?> resp = service.getInfo(req);
		assertNotNull(resp.getBody());
		assertEquals(resp.getBody(), jsonResp);
		assertNotEquals(resp.getStatusCodeValue(), 200);

	}
}
