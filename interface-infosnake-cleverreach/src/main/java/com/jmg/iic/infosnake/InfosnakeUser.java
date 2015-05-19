package com.jmg.iic.infosnake;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class to encapsulate a Infosnake's user
 * 
 * @author Javier Moreno Garcia
 *
 */
public class InfosnakeUser {

	@JsonProperty("ID_WEB")
	private Integer idWeb;

	@JsonProperty("FIRST_NAME")
	private String firstname;

	@JsonProperty("LAST_NAME")
	private String lastname;

	@JsonProperty("EMAIL")
	private String email;

	public Integer getIdWeb() {
		return this.idWeb;
	}

	public void setIdWeb(Integer idWeb) {
		this.idWeb = idWeb;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this).toString();

	}

}
