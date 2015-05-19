/**
 * 
 */
package com.jmg.iic.cleverreach;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Javier Moreno Garcia
 *
 */
public class CleverreachReceiver {

	private String email;

	private Boolean flagImport;

	private String firstname;

	private String lastname;

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getFlagImport() {
		return this.flagImport;
	}

	public void setFlagImport(Boolean flagImport) {
		this.flagImport = flagImport;
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

	@Override
	public boolean equals(Object obj) {

		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		CleverreachReceiver other = (CleverreachReceiver) obj;

		return new EqualsBuilder().append(this.email, other.email).isEquals();

	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(email).toHashCode();

	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this).toString();

	}

}
