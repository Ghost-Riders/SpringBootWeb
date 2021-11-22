package org.example.test.service;

import java.util.Collection;

import org.example.test.model.Greetings;
import org.example.test.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GreetingServiceBean implements GreetingService {

	@Autowired
	private GreetingRepository greetingRepository;

	@Override
	public Collection<Greetings> findAll() {
		Collection<Greetings> greetings = greetingRepository.findAll();
		return greetings;
	}

	@Override
	public Greetings findOne(long id) {
		Greetings greeting = greetingRepository.findById(id).get();
		return greeting;
	}

	@Override
	public Greetings createGreeting(Greetings greetings) {
		if (greetings.getId() != null) {
			return null;
		}
		Greetings createGreeting = greetingRepository.save(greetings);
		return createGreeting;
	}

	@Override
	public Greetings updateGreeting(Greetings greetings) {
		if (greetings.getId() == null) {
			return null;
		}
		Greetings updateGreeting = greetingRepository.save(greetings);
		return updateGreeting;
	}

	@Override
	public void deleteGreeting(long id) {
		greetingRepository.deleteById(id);
	}

}
