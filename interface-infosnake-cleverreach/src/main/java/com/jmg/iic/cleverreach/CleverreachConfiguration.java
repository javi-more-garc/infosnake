/**
 * 
 */
package com.jmg.iic.cleverreach;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

/**
 * @author Javier Moreno Garcia
 *
 */
@Configuration
public class CleverreachConfiguration {

	@Inject
	private Environment env;

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("com.jmg.iic.cleverreach.xml");

		return marshaller;
	}

	@Bean
	public CleverreachClientImpl cleverreachClient(Jaxb2Marshaller marshaller) {

		// get properties

		String url = env.getRequiredProperty("cleverreach.api.url");
		String apiKey = env.getRequiredProperty("cleverreach.api.key");
		Integer listId = env.getRequiredProperty("cleverreach.list.id", Integer.class);

		CleverreachClientImpl client = new CleverreachClientImpl(url, apiKey, listId);

		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
}
