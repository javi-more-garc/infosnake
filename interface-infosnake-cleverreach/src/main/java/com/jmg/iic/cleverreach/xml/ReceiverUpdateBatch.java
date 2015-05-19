package com.jmg.iic.cleverreach.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "receiverUpdateBatch")
public class ReceiverUpdateBatch {

	@XmlElement(required = true)
	protected java.lang.String apiKey;
	protected int listId;

	@XmlElement(name = "userData")
	protected UserDataWrapper userDataWrapper = new UserDataWrapper();

	public java.lang.String getApiKey() {
		return this.apiKey;
	}

	public void setApiKey(java.lang.String apiKey) {
		this.apiKey = apiKey;
	}

	public int getListId() {
		return this.listId;
	}

	public void setListId(int listId) {
		this.listId = listId;
	}

	public List<ReceiverData> getUserData() {
		return this.userDataWrapper.getUserData();
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	public static class UserDataWrapper {

		@XmlAttribute(name = "xsi:type")
		protected String type = "SOAP-ENC:Array";

		@XmlElement(name = "item")
		protected List<ReceiverData> userData = new ArrayList<ReceiverData>();

		public List<ReceiverData> getUserData() {
			return this.userData;
		}

		public void setUserData(List<ReceiverData> userData) {
			this.userData = userData;
		}

		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this);

		}

	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);

	}

}
