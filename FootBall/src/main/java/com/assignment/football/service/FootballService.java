package com.assignment.football.service;


import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface FootballService {
  ResponseEntity<?> getInfo(HttpServletRequest paramHttpServletRequest) throws Exception;
}
