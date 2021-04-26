package com.assignment.football.serviceImpl;

import com.assignment.football.service.FootballService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Service
public class FootballServiceInfoImpl implements FootballService {
	Logger LOG = LogManager.getLogger(FootballServiceInfoImpl.class);
	@Autowired
	private RestTemplate restCleint;

	@Value("${base.url}")
	String baseUrl;

	@Value("${api.key}")
	String accessKey;

	public ResponseEntity<?> getInfo(HttpServletRequest request) throws Exception {
		LOG.info("Request Received with params: {}", request.getParameterMap());
		try {
			MultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<>();
			for (Map.Entry<String, String[]> m : request.getParameterMap().entrySet())
				linkedMultiValueMap.add(m.getKey(), m.getValue()[0]);
			linkedMultiValueMap.add("APIkey", accessKey);
			UriComponents uri = UriComponentsBuilder.newInstance().host(baseUrl).scheme("https")
					.queryParams(linkedMultiValueMap).build();
			
			LOG.info("Connecting to the host: {} ....",baseUrl);
			
			ResponseEntity<String> response = this.restCleint.getForEntity(uri.toUriString(), String.class,
					new Object[0]);
			LOG.info("Request Status: [{}]", response.getStatusCodeValue());
			
			return new ResponseEntity<String>(response.getBody(), HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("FAILED TO SERVE REQUEST DUE TO: ", e);
			return new ResponseEntity<String>("FAILURE HAPPENED :( !", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
