package org.example.test.service;

import java.util.Collection;

import org.example.test.model.Greetings;

public interface GreetingService {
	Collection<Greetings> findAll();

	Greetings findOne(long id);

	Greetings createGreeting(Greetings greetings);

	Greetings updateGreeting(Greetings greetings);

	void deleteGreeting(long id);

}
