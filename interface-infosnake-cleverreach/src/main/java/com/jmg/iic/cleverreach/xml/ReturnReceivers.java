package com.jmg.iic.cleverreach.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "returnReceivers")
public class ReturnReceivers {

	@XmlElement(required = true)
	protected String status;
	@XmlElement(required = true)
	protected String message;
	protected int statuscode;

	@XmlElementWrapper(name = "data")
	@XmlElement(name = "item")
	protected List<ReceiverData> receiverData = new ArrayList<ReceiverData>();

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatuscode() {
		return this.statuscode;
	}

	public void setStatuscode(int statuscode) {
		this.statuscode = statuscode;
	}

	public List<ReceiverData> getReceiverData() {
		return this.receiverData;
	}

	public void setReceiverData(List<ReceiverData> receiverData) {
		this.receiverData = receiverData;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);

	}

}
