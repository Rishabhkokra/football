package com.assignment.football.controller;

import com.assignment.football.service.FootballService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({ "/api/v1" })
public class FootBallController {
	@Autowired
	private FootballService footballService;

	@GetMapping({ "/info/health" })
	public String healthCheck() {
		return "i am ok!";
	}

	@GetMapping({ "/info/football" })
	public ResponseEntity<?> getInfo(HttpServletRequest request) throws Exception {
		return this.footballService.getInfo(request);
	}
}
