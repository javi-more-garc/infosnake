package com.jmg.iic.infosnake;

import static org.springframework.util.Assert.notNull;

import java.net.URI;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.protocol.HttpContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

/**
 * 
 * @author Javier Moreno Garcia
 *
 */
public class BasicAuthHttpComponentsClientHttpRequestFactory extends HttpComponentsClientHttpRequestFactory {

	private HttpHost host;
	private UsernamePasswordCredentials credentials;

	public BasicAuthHttpComponentsClientHttpRequestFactory(HttpHost host, UsernamePasswordCredentials credentials) {
		super();
		// validation
		notNull(host);
		notNull(credentials);

		this.host = host;
		this.credentials = credentials;
	}

	protected HttpContext createHttpContext(HttpMethod httpMethod, URI uri) {
		return createHttpContext();
	}

	private HttpContext createHttpContext() {

		// create AuthCache instance
		AuthCache authCache = new BasicAuthCache();
		// generate BASIC scheme object and add it to the local auth cache
		BasicScheme basicAuth = new BasicScheme();
		authCache.put(this.host, basicAuth);

		// add AuthCache to the execution context
		HttpClientContext localcontext = HttpClientContext.create();
		localcontext.setAuthCache(authCache);

		BasicCredentialsProvider credsProvider = new BasicCredentialsProvider();
		// change if we need more fine grained scope
		credsProvider.setCredentials(AuthScope.ANY, this.credentials);

		localcontext.setCredentialsProvider(credsProvider);

		return localcontext;
	}
}
