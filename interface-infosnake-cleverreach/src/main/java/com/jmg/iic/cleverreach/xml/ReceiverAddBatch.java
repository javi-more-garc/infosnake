package com.jmg.iic.cleverreach.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "receiverAddBatch")
public class ReceiverAddBatch {

	@XmlElement(required = true)
	protected String apiKey;
	protected int listId;

	@XmlElementWrapper(name = "data")
	@XmlElement(name = "item")
	protected List<ReceiverData> subscriberData = new ArrayList<ReceiverData>();

	public String getApiKey() {
		return this.apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public int getListId() {
		return this.listId;
	}

	public void setListId(int listId) {
		this.listId = listId;
	}

	public List<ReceiverData> getSubscriberData() {
		return this.subscriberData;
	}

	public void setSubscriberData(List<ReceiverData> subscriberData) {
		this.subscriberData = subscriberData;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);

	}

}
