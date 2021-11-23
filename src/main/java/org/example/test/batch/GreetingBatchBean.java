package org.example.test.batch;

import java.util.Collection;

import org.example.test.model.Greetings;
import org.example.test.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GreetingBatchBean {
	@Autowired
	private GreetingService greetingService;

	@Autowired
	final static Logger LOGGER = LoggerFactory.getLogger(GreetingBatchBean.class);

	// will run for every 30 secon after application start
//	@Scheduled(cron = "0,30 * * * * *")
	public void cronJob() {
		LOGGER.info("CronJob");
		System.out.println("Running crontab");
		Collection<Greetings> greetings = greetingService.findAll();
		LOGGER.info("There are {} greetings in the data store", greetings.size());
		LOGGER.info("CronJob");
	}

	// will run first at 5 second and after that 10 sec[because 15 sec after app
	// start
//	@Scheduled(initialDelay = 5000, fixedRate = 15000)
	public void fixedRateJobWtihInitialDelay() {
		LOGGER.info(">fixedRateJobWtihInitialDelay");
		long pause = 5000;
		long start = System.currentTimeMillis();
		do {
			if (start + pause < System.currentTimeMillis()) {
				break;
			}
		} while (true);
		LOGGER.info("processing time was {} seconds", pause / 1000);
		LOGGER.info(">fixedRateJobWtihInitialDelay");
	}

	// will run first 5 sec, continued and run after 15 sec
	@Scheduled(initialDelay = 5000, fixedDelay = 15000)
	public void fixedDelayJobWtihInitialDelay() {
		LOGGER.info(">fixedDelayJobWithInitialdelay");
		long pause = 5000;
		long start = System.currentTimeMillis();
		do {
			if (start + pause < System.currentTimeMillis()) {
				break;
			}
		} while (true);
		LOGGER.info("processing time was {} seconds", pause / 1000);
		LOGGER.info(">fixedDelayJobWithInitialdelay");
	}

}
