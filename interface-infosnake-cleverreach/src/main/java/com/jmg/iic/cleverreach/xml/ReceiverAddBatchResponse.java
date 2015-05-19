package com.jmg.iic.cleverreach.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "receiverAddBatchResponse")
public class ReceiverAddBatchResponse {

	@XmlElement(name = "return", required = true)
	protected ReturnString _return;

	public ReturnString getReturn() {
		return _return;
	}

	public void setReturn(ReturnString value) {
		this._return = value;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);

	}

}
