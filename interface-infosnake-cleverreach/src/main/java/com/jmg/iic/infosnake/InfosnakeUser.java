package com.jmg.iic.infosnake;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InfosnakeUser {

	@JsonProperty("ID_WEB")
	private Integer idWeb;

	@JsonProperty("FIRST_NAME")
	private String firstName;

	@JsonProperty("LAST_NAME")
	private String LastName;

	@JsonProperty("EMAIL")
	private String email;

	public Integer getIdWeb() {
		return this.idWeb;
	}

	public void setIdWeb(Integer idWeb) {
		this.idWeb = idWeb;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.LastName;
	}

	public void setLastName(String lastName) {
		this.LastName = lastName;
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
