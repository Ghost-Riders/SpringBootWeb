package org.example.test.service;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.example.test.model.Greetings;
import org.springframework.stereotype.Service;

@Service
public class GreetingServiceBean implements GreetingService {

	private static BigInteger nextInt;
	private static Map<BigInteger, Greetings> greetingMap;

	private static Greetings save(Greetings greetings) {
		if (greetingMap == null) {
			greetingMap = new HashMap<BigInteger, Greetings>();
			nextInt = BigInteger.ONE;
		}
// IF update...
		if (greetings.getId() != null) {
			Greetings oldGreetings = greetingMap.get(greetings.getId());
			if (oldGreetings == null) {
				return null;
			}
			greetingMap.remove(greetings.getId());
			greetingMap.put(greetings.getId(), greetings);
			return greetings;
		}
// IF create...
		greetings.setId(nextInt);
		nextInt = nextInt.add(BigInteger.ONE);
		greetingMap.put(greetings.getId(), greetings);
		return greetings;
	}

	static {
		Greetings g1 = new Greetings();
		g1.setText("hello manmath");
		save(g1);
		Greetings g2 = new Greetings();
		g2.setText("welcome to mindtree");
		save(g2);
	}

	@Override
	public Collection<Greetings> findAll() {
		Collection<Greetings> greetings = greetingMap.values();
		return greetings;
	}

	@Override
	public Greetings findOne(BigInteger id) {
		Greetings greeting = greetingMap.get(id);
		return greeting;
	}

	@Override
	public Greetings createGreeting(Greetings greetings) {
		Greetings createGreeting = save(greetings);
		return createGreeting;
	}

	@Override
	public Greetings updateGreeting(Greetings greetings) {
		Greetings updateGreeting = save(greetings);
		return updateGreeting;
	}

	@Override
	public void deleteGreeting(BigInteger id) {
		greetingMap.remove(id);
	}

}
