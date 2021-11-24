package org.example.test.service;

import java.util.Collection;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

import org.example.test.model.Greetings;
import org.example.test.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GreetingServiceBean implements GreetingService {

	@Autowired
	private GreetingRepository greetingRepository;

	@Override
	public Collection<Greetings> findAll() {
		Collection<Greetings> greetings = greetingRepository.findAll();
		return greetings;
	}

	@Override
	@Cacheable(value = "greetings", key = "#id")
	public Greetings findOne(long id) {
		Greetings greeting = greetingRepository.findById(id).orElse(null);
		return greeting;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CachePut(value = "greetings", key = "#result.id")
	public Greetings createGreeting(Greetings greetings) {
		if (greetings.getId() != null) {
			throw new EntityExistsException("Id must be null to persist");
		}
		Greetings createGreeting = greetingRepository.save(greetings);
		if (greetings.getId() == 7L) {
			throw new RuntimeException("Roll me back");
		}
		return createGreeting;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CachePut(value = "greetings", key = "#greeting.id")
	public Greetings updateGreeting(Greetings greeting) {
		if (greeting.getId() == null) {
			throw new NoResultException("Requested entity not found");
		}
		Optional<Greetings> optionsGreeting=greetingRepository.findById(greeting.getId());
		if(!optionsGreeting.isPresent()) {
			throw new NoResultException();
		}
		Greetings greetingToUpdate = optionsGreeting.get();
		greetingToUpdate.setText(greeting.getText());
		Greetings updateGreeting = greetingRepository.save(greetingToUpdate);
		return updateGreeting;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CacheEvict(value = "greetings", key = "#id")
	public void deleteGreeting(long id) {
		greetingRepository.deleteById(id);
	}

	@Override
	@CacheEvict(value = "greetings", allEntries = true)
	public void evictCache() {
	}

}
