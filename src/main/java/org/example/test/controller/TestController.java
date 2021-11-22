package org.example.test.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@RequestMapping(value = "/home", method = RequestMethod.GET, produces = MediaType.ALL_VALUE)
	public String getTest() {
		return "welcome";
	}
}
