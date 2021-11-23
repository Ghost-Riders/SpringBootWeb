package org.example.test.controller;

import java.util.Collection;

import org.example.test.model.Greetings;
import org.example.test.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	@Autowired
	final static Logger LOGGER = LoggerFactory.getLogger(GreetingController.class);

	@RequestMapping(value = "/api/greetings", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Greetings>> getGreetings() {
		LOGGER.info("start: all greetings");
		Collection<Greetings> greetings = greetingService.findAll();
		LOGGER.info("end: all greetings");
		return new ResponseEntity<Collection<Greetings>>(greetings, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/greetings/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greetings> getGreeting(@PathVariable("id") Long id) {
		LOGGER.info("start: get greetings");
		Greetings greeting = greetingService.findOne(id);
		if (greeting == null) {
			return new ResponseEntity<Greetings>(HttpStatus.NOT_FOUND);
		}
		LOGGER.info("end: get greetings");
		return new ResponseEntity<Greetings>(greeting, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/greetings", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greetings> createGreeting(@RequestBody Greetings greetings) {
		LOGGER.info("start: create greetings");
		Greetings saveGreetings = greetingService.createGreeting(greetings);
		LOGGER.info("end: create greetings");
		return new ResponseEntity<Greetings>(saveGreetings, HttpStatus.CREATED);

	}

	@RequestMapping(value = "/api/greetings/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greetings> updateGreeting(@RequestBody Greetings greetings) {
		LOGGER.info("start: update greetings");
		Greetings updatedGreetings = greetingService.updateGreeting(greetings);
		if (updatedGreetings == null) {
			return new ResponseEntity<Greetings>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		LOGGER.info("end: update greetings");
		return new ResponseEntity<Greetings>(updatedGreetings, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/greetings/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greetings> deleteGreeting(@PathVariable("id") Long id) {
		LOGGER.info("start: delete greetings");
		greetingService.deleteGreeting(id);
		LOGGER.info("end: delete greetings");
		return new ResponseEntity<Greetings>(HttpStatus.NO_CONTENT);
	}

}
