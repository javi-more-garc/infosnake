/**
 * 
 */
package com.jmg.iic.infosnake;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Lists;

/**
 * @author Javier Moreno Garcia
 *
 */
public class InfosnakeClientImpl implements InfosnakeClient {

	private static final String DEFAULT_URL = "http://www.infosnake.ch/prev/cleverreach_interface/recipients.json";

	private Logger logger = LoggerFactory.getLogger(InfosnakeClientImpl.class);

	@Inject
	private RestTemplate infosnakeRestTemplate;

	private String url;

	public InfosnakeClientImpl() {
		this.url = DEFAULT_URL;
	}

	public InfosnakeClientImpl(String url) {
		this.url = url;
	}

	@Override
	public List<InfosnakeUser> getAllUsers() {

		logger.info("Requesting Infosnake's method 'recipients'.");

		// retrieve the list of all users
		ResponseEntity<InfosnakeUser[]> response = infosnakeRestTemplate.getForEntity(url, InfosnakeUser[].class);

		if (response.getStatusCode() != HttpStatus.OK) {
			// TODO throw ex
			logger.info("Returning: status '{}'.", response.getStatusCode());
			return null;
		} else {
			List<InfosnakeUser> result = Lists.newArrayList(response.getBody());
			logger.info("Returning: status 'OK' with body '{}'.", result);

			return result;

		}

	}
}
