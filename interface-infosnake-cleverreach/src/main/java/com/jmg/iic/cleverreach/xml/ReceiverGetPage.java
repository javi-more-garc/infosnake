//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.05.18 at 05:59:47 PM CEST 
//

package com.jmg.iic.cleverreach.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;

@XmlRootElement(name = "receiverGetPage")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReceiverGetPage {

	@XmlElement(required = true)
	protected String apiKey;
	protected int listId;
	@XmlElement(required = true)
	protected PageFilterSegment page;

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

	public PageFilterSegment getPage() {
		return this.page;
	}

	public void setPage(PageFilterSegment page) {
		this.page = page;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);

	}

}
