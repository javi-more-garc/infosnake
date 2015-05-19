package com.jmg.iic.cleverreach.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;

@XmlRootElement(name = "receiverGetPageResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReceiverGetPageResponse {

	@XmlElement(name = "return", required = true)
	protected ReturnReceivers _return;

	public ReturnReceivers getReturn() {
		return this._return;
	}

	public void setReturn(ReturnReceivers _return) {
		this._return = _return;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);

	}

}
