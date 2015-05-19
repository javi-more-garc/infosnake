/**
 * 
 */
package com.jmg.iic.core;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Javier Moreno Garcia
 *
 */

@Component
public class Scheduler {

	private Logger logger = LoggerFactory.getLogger(Scheduler.class);

	@Inject
	private MatchService matchService;

	@Scheduled(fixedDelayString = "${scheduler.fixed.delay}", initialDelayString = "${scheduler.initial.delay}")
	public void updateMembers() {
		try {
			logger.info("Starting Infosnake-Cleverreach matching process...");

			matchService.matchValues();

			logger.info("Finished Infosnake-Cleverreach matching process successfully");
		} catch (Exception e) {
			logger.error("Scheduler execution failed", e);
		}
	}
}
