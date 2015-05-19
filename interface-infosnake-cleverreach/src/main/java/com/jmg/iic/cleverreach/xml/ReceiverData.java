package com.jmg.iic.cleverreach.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.ToStringBuilder;

@XmlAccessorType(XmlAccessType.FIELD)
public class ReceiverData {

	protected Integer id;
	@XmlElement(required = true)
	protected String email;
	protected Integer registered;
	protected Integer activated;
	protected Integer deactivated;
	protected Boolean active;
	protected String source;
	protected Integer quality;

	@XmlElement(name = "attributes")
	private ReceiverDataWrapper wrapper = new ReceiverDataWrapper();

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getRegistered() {
		return this.registered;
	}

	public void setRegistered(Integer registered) {
		this.registered = registered;
	}

	public Integer getActivated() {
		return this.activated;
	}

	public void setActivated(Integer activated) {
		this.activated = activated;
	}

	public Integer getDeactivated() {
		return this.deactivated;
	}

	public void setDeactivated(Integer deactivated) {
		this.deactivated = deactivated;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getQuality() {
		return this.quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}

	public ReceiverDataWrapper getWrapper() {
		return this.wrapper;
	}

	public void setWrapper(ReceiverDataWrapper wrapper) {
		this.wrapper = wrapper;
	}

	public List<ReceiverAttribute> getReceiverAttributes() {
		return this.wrapper.getReceiverAttributes();
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	public static class ReceiverDataWrapper {

		@XmlAttribute(name = "xsi:type")
		protected String type = "SOAP-ENC:Array";

		@XmlElement(name = "item")
		protected List<ReceiverAttribute> receiverAttributes = new ArrayList<ReceiverAttribute>();

		public List<ReceiverAttribute> getReceiverAttributes() {
			return this.receiverAttributes;
		}

		public void setReceiverAttributes(List<ReceiverAttribute> receiverAttributes) {
			this.receiverAttributes = receiverAttributes;
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
