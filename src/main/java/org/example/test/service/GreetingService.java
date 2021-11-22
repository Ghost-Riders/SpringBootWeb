package org.example.test.service;

import java.math.BigInteger;
import java.util.Collection;

import org.example.test.model.Greetings;

public interface GreetingService {
	Collection<Greetings> findAll();

	Greetings findOne(BigInteger id);

	Greetings createGreeting(Greetings greetings);

	Greetings updateGreeting(Greetings greetings);

	void deleteGreeting(BigInteger id);

}
