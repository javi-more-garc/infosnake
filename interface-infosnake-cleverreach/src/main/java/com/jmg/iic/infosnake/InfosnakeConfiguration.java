/**
 * 
 */
package com.jmg.iic.infosnake;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.http.HttpHost;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * @author Javier Moreno Garcia
 *
 */

@Configuration
public class InfosnakeConfiguration {

	private static final int READ_TIME_OUT = 5000;
	private static final int CONNECTION_TIME_OUT = 5000;

	@Inject
	private Environment env;

	@Bean
	public RestTemplate infosnakeRestTemplate() {
		// instantiate rest template
		RestTemplate restTemplate = new RestTemplate(requestFactory());

		// so far this is the only converter we need
		MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();

		// the server seems to send a Text Plain for JSON
		List<MediaType> jacksonSupported = new ArrayList<MediaType>(jacksonConverter.getSupportedMediaTypes());

		// so we need to add it
		jacksonSupported.add(MediaType.TEXT_PLAIN);

		jacksonConverter.setSupportedMediaTypes(jacksonSupported);

		restTemplate.getMessageConverters().add(jacksonConverter);

		return restTemplate;
	}

	@Bean
	public InfosnakeClient infosnakeClient() {
		return new InfosnakeClientImpl(env.getRequiredProperty("infosnake.url"));
	}

	//
	// private methods

	private HttpComponentsClientHttpRequestFactory requestFactory() {

		// retrieve properties
		String hostname = env.getRequiredProperty("infosnake.host");
		int port = env.getRequiredProperty("infosnake.port", Integer.class);

		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
				env.getRequiredProperty("infosnake.user.username"), env.getRequiredProperty("infosnake.user.password"));

		// create host
		HttpHost targetHost = new HttpHost(hostname, port);

		// create factory
		BasicAuthHttpComponentsClientHttpRequestFactory requestFactory = new BasicAuthHttpComponentsClientHttpRequestFactory(
				targetHost, credentials);

		requestFactory.setReadTimeout(READ_TIME_OUT);
		requestFactory.setConnectTimeout(CONNECTION_TIME_OUT);

		return requestFactory;

	}
}
