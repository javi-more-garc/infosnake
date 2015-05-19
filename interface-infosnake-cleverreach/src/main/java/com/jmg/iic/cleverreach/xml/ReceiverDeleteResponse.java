package com.jmg.iic.cleverreach.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "receiverDeleteResponse")
public class ReceiverDeleteResponse {

	@XmlElement(name = "return", required = true)
	protected ReturnString _return;

	public ReturnString getReturn() {
		return this._return;
	}

	public void setReturn(ReturnString _return) {
		this._return = _return;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);

	}

}
