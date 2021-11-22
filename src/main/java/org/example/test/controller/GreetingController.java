package org.example.test.controller;

import java.math.BigInteger;
import java.util.Collection;

import org.example.test.model.Greetings;
import org.example.test.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	@Autowired
	private GreetingService greetingService;

	@RequestMapping(value = "/api/greetings", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Greetings>> getGreetings() {
		Collection<Greetings> greetings = greetingService.findAll();
		return new ResponseEntity<Collection<Greetings>>(greetings, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/greetings/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greetings> getGreeting(@PathVariable("id") BigInteger id) {
		Greetings greeting = greetingService.findOne(id);
		if (greeting == null) {
			return new ResponseEntity<Greetings>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Greetings>(greeting, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/greetings", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greetings> createGreeting(@RequestBody Greetings greetings) {
		Greetings saveGreetings = greetingService.createGreeting(greetings);
		return new ResponseEntity<Greetings>(saveGreetings, HttpStatus.CREATED);

	}

	@RequestMapping(value = "/api/greetings/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greetings> updateGreeting(@RequestBody Greetings greetings) {
		Greetings updatedGreetings = greetingService.updateGreeting(greetings);
		if (updatedGreetings == null) {
			return new ResponseEntity<Greetings>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Greetings>(updatedGreetings, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/greetings/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greetings> deleteGreeting(@PathVariable("id") BigInteger id) {
		return new ResponseEntity<Greetings>(HttpStatus.NO_CONTENT);
	}

}
