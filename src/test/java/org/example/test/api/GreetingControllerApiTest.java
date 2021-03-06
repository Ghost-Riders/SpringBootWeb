package org.example.test.api;

import org.example.test.AbstractControllerTest;
import org.example.test.model.Greetings;
import org.example.test.service.GreetingService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class GreetingControllerApiTest extends AbstractControllerTest {

	@Autowired
	private GreetingService greetingService;

	@Before
	public void setUp() {
		super.setUp();
		greetingService.evictCache();
	}

	@Test
	public void testGetGreetings() throws Exception {
		final String url = "/api/greetings";

		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON)).andReturn();

		String content = result.getResponse().getContentAsString();

		int statusCode = result.getResponse().getStatus();

		Assert.assertEquals("Failure - excpected status code 200", 200, statusCode);
		Assert.assertTrue("Failure - expected result as value", content.trim().length() > 0);
	}

	@Test
	public void testGetGreeting() throws Exception {

		String uri = "/api/greetings/{id}";
		Long id = Long.valueOf(2);

		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		logger.error(result.getResponse().getContentAsString());
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		Assert.assertEquals("failure - expected HTTP status 200", 200, status);
		Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);

	}

	@Test
	public void testGetGreetingNotFound() throws Exception {

		String uri = "/api/greetings/{id}";
		Long id = Long.MAX_VALUE;

		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON))
				.andReturn();

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		Assert.assertEquals("failure - expected HTTP status 404", 404, status);
		Assert.assertTrue("failure - expected HTTP response body to be empty", content.trim().length() == 0);

	}

	@Test
	public void testCreateGreeting() throws Exception {

		String uri = "/api/greetings";
		Greetings greeting = new Greetings();
		greeting.setText("test");
		String inputJson = super.mapToJson(greeting);

		MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		Assert.assertEquals("failure - expected HTTP status 201", 201, status);
		Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);

		Greetings createdGreeting = super.mapFromJson(content, Greetings.class);

		Assert.assertNotNull("failure - expected greeting not null", createdGreeting);
		Assert.assertNotNull("failure - expected greeting.id not null", createdGreeting.getId());
		Assert.assertEquals("failure - expected greeting.text match", "test", createdGreeting.getText());

	}

	@Test
	public void testUpdateGreeting() throws Exception {

		String uri = "/api/greetings/{id}";
		Long id = Long.valueOf(2);
		Greetings greeting = greetingService.findOne(id);
		String updatedText = greeting.getText() + " test";
		greeting.setText(updatedText);
		String inputJson = super.mapToJson(greeting);

		MvcResult result = mvc.perform(MockMvcRequestBuilders.put(uri, id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		Assert.assertEquals("failure - expected HTTP status 200", 200, status);
		Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);

		Greetings updatedGreeting = super.mapFromJson(content, Greetings.class);

		Assert.assertNotNull("failure - expected greeting not null", updatedGreeting);
		Assert.assertEquals("failure - expected greeting.id unchanged", greeting.getId(), updatedGreeting.getId());
		Assert.assertEquals("failure - expected updated greeting text match", updatedText, updatedGreeting.getText());

	}

	@Test
	public void testDeleteGreeting() throws Exception {

		String uri = "/api/greetings/{id}";
		Long id = Long.valueOf(2);

		MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(uri, id).contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		Assert.assertEquals("failure - expected HTTP status 204", 204, status);
		Assert.assertTrue("failure - expected HTTP response body to be empty", content.trim().length() == 0);

		Greetings deletedGreeting = greetingService.findOne(id);

		Assert.assertNull("failure - expected greeting to be null", deletedGreeting);

	}
}
