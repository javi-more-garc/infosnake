package com.jmg.iic.cleverreach.xml;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

	public ObjectFactory() {
	}

	// get

	public ReceiverGetPage createReceiverGetPage() {
		return new ReceiverGetPage();
	}

	public ReceiverGetPageResponse createReceiverGetPageResponse() {
		return new ReceiverGetPageResponse();
	}

	// add batch

	public ReceiverAddBatch createReceiverAddBatch() {
		return new ReceiverAddBatch();
	}

	public ReceiverAddBatchResponse createReceiverAddBatchResponse() {
		return new ReceiverAddBatchResponse();
	}

	// update batch

	public ReceiverUpdateBatch createReceiverUpdateBatch() {
		return new ReceiverUpdateBatch();
	}

	public ReceiverUpdateBatchResponse createReceiverUpdateBatchResponse() {
		return new ReceiverUpdateBatchResponse();
	}

	// remove

	public ReceiverDelete createReceiverDelete() {
		return new ReceiverDelete();
	}

	public ReceiverDeleteResponse createReceiverDeleteResponse() {
		return new ReceiverDeleteResponse();
	}

}
