package org.example.test.service;

import java.util.Collection;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

import org.example.test.AbstractTest;
import org.example.test.model.Greetings;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class GreetingServiceTest extends AbstractTest {

	@Autowired
	private GreetingService service;

	@Before
	public void setUp() {
		service.evictCache();
	}

	@After
	public void tearDown() {
		// clean up after each test method
	}

	@Test
	public void testFindAll() {

		Collection<Greetings> list = service.findAll();

		Assert.assertNotNull("failure - expected not null", list);
		Assert.assertEquals("failure - expected list size", 6, list.size());

	}

	@Test
	public void testFindOne() {

		Long id = new Long(2);

		Greetings entity = service.findOne(id);

		Assert.assertNotNull("failure - expected not null", entity);
		Assert.assertEquals("failure - expected id attribute match", id, entity.getId());

	}

	@Test
	public void testFindOneNotFound() {

		Long id = Long.MAX_VALUE;

		Greetings entity = service.findOne(id);

		Assert.assertNull("failure - expected null", entity);

	}

	@Test
	public void testCreate() {

		Greetings entity = new Greetings();
		entity.setText("test");

		Greetings createdEntity = service.createGreeting(entity);

		Assert.assertNotNull("failure - expected not null", createdEntity);
		Assert.assertNotNull("failure - expected id attribute not null", createdEntity.getId());
		Assert.assertEquals("failure - expected text attribute match", "test", createdEntity.getText());

		Collection<Greetings> list = service.findAll();

		Assert.assertEquals("failure - expected size", 7, list.size());

	}

	@Test
	public void testCreateWithId() {

		Exception exception = null;

		Greetings entity = new Greetings();
		entity.setId(Long.MAX_VALUE);
		entity.setText("test");

		try {
			service.createGreeting(entity);
		} catch (EntityExistsException e) {
			exception = e;
		} catch (Exception e) {
			exception = e;
			System.out.println(e.getCause());
		}

		Assert.assertNotNull("failure - expected exception", exception);
		System.out.println("testing " + exception);
		Assert.assertTrue("failure - expected EntityExistsException", exception instanceof EntityExistsException);

	}

	@Test
	public void testUpdate() {

		Long id = new Long(3);

		Greetings entity = service.findOne(id);

		Assert.assertNotNull("failure - expected not null", entity);

		String updatedText = entity.getText() + " test";
		entity.setText(updatedText);

		Greetings updatedEntity = service.updateGreeting(entity);

		Assert.assertNotNull("failure - expected not null", updatedEntity);
		Assert.assertEquals("failure - expected id attribute match", id, updatedEntity.getId());
		Assert.assertEquals("failure - expected text attribute match", updatedText, updatedEntity.getText());

	}

	@Test
	public void testUpdateNotFound() {

		Exception exception = null;

		Greetings entity = new Greetings();
		entity.setId(Long.MAX_VALUE);
		entity.setText("test");

		try {
			service.updateGreeting(entity);
		} catch (NoResultException e) {
			exception = e;
		}

		Assert.assertNotNull("failure - expected exception", exception);
		Assert.assertTrue("failure - expected NoResultException", exception instanceof NoResultException);

	}

	@Test
	public void testDelete() {

		Long id = new Long(8);

		Greetings entity = service.findOne(id);

		Assert.assertNotNull("failure - expected not null", entity);

		service.deleteGreeting(id);

		Collection<Greetings> list = service.findAll();

		Assert.assertEquals("failure - expected size", 5, list.size());

		Greetings deletedEntity = service.findOne(id);

		Assert.assertNull("failure - expected null", deletedEntity);

	}
}
