/**
 * 
 */
package com.jmg.iic.cleverreach;

import static java.util.Arrays.asList;
import static org.springframework.util.Assert.notNull;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.google.common.collect.Lists;
import com.jmg.iic.cleverreach.xml.PageFilterSegment;
import com.jmg.iic.cleverreach.xml.ReceiverAddBatch;
import com.jmg.iic.cleverreach.xml.ReceiverAddBatchResponse;
import com.jmg.iic.cleverreach.xml.ReceiverAttribute;
import com.jmg.iic.cleverreach.xml.ReceiverData;
import com.jmg.iic.cleverreach.xml.ReceiverDelete;
import com.jmg.iic.cleverreach.xml.ReceiverDeleteResponse;
import com.jmg.iic.cleverreach.xml.ReceiverGetPage;
import com.jmg.iic.cleverreach.xml.ReceiverGetPageResponse;
import com.jmg.iic.cleverreach.xml.ReceiverUpdateBatch;
import com.jmg.iic.cleverreach.xml.ReceiverUpdateBatchResponse;
import com.jmg.iic.core.GeneralException;

/**
 * 
 * @author Javier Moreno Garcia
 *
 */

public class CleverreachClientImpl extends WebServiceGatewaySupport implements CleverreachClient {

	private static final String DEFAULT_URL = "http://api.cleverreach.com/soap/interface_v5.1.php";

	private static final int MAX_RESULTS_IN_SEARCH = 100;

	private static final int MAX_OPERATIONS_IN_BATCH = 50;

	private static final String ATT_FLAG_IMPORT_KEY = "flag_import";

	private static final String ATT_FLAG_IMPORT_VALUE_TRUE = "1";

	private static final String ATT_FIRSTNAME_KEY = "first_name";

	private static final String ATT_LASTNAME_KEY = "last_name";

	private static final String PARAM_FILER_ALL_VALUE = "all";

	private Logger logger = LoggerFactory.getLogger(CleverreachClientImpl.class);

	private String apiKey;
	private Integer listId;

	public CleverreachClientImpl(String apiKey, Integer listId) {
		this(DEFAULT_URL, apiKey, listId);
	}

	public CleverreachClientImpl(String url, String apiKey, Integer listId) {
		notNull(url);
		notNull(apiKey);
		notNull(listId);

		this.apiKey = apiKey;
		this.listId = listId;

		setDefaultUri(url);

	}

	@Override
	public List<CleverreachReceiver> getAllReceivers() {

		List<CleverreachReceiver> results = new ArrayList<CleverreachReceiver>();

		List<CleverreachReceiver> tmpResults = null;

		int page = 0;

		do {

			tmpResults = getReceiversByPage(page);

			results.addAll(tmpResults);

			page++;

		} while (tmpResults.size() == MAX_RESULTS_IN_SEARCH);

		return results;
	}

	@Override
	public void addReceivers(Set<CleverreachReceiver> receivers) {
		// validation
		notNull(receivers);

		// split receiver in lots
		List<List<CleverreachReceiver>> lots = Lists.partition(Lists.newArrayList(receivers), MAX_OPERATIONS_IN_BATCH);

		for (List<CleverreachReceiver> lot : lots) {
			addReceiversInBatch(lot);
		}

	}

	@Override
	public void removeReceivers(Set<String> emails) {
		// validation
		notNull(emails);

		// iterate one by one
		for (String email : emails) {
			receiverDelete(email);
		}

	}

	@Override
	public void updateReceivers(Set<CleverreachReceiver> receivers) {
		// validation
		notNull(receivers);

		// split receiver in lots
		List<List<CleverreachReceiver>> lots = Lists.partition(Lists.newArrayList(receivers), MAX_OPERATIONS_IN_BATCH);

		for (List<CleverreachReceiver> lot : lots) {
			updateReceiversInBatch(lot);
		}

	}

	//
	// private methods

	private Object perform(Object request, String method) {

		logger.info("Requesting Cleverreach's method '{}' with input '{}'", method, request);

		Object response = getWebServiceTemplate().marshalSendAndReceive(request, new SoapActionCallback(method));

		logger.info("Returning: {}", response);

		return response;

	}

	private List<CleverreachReceiver> getReceiversByPage(Integer page) {

		ReceiverGetPage request = newReceiverGetPageRequest(page);

		ReceiverGetPageResponse response = (ReceiverGetPageResponse) perform(request, "receiverGetPage");

		checkResponseStatusCode(response.getReturn().getStatuscode(), "receiverGetPage");

		return toResult(response);

	}

	private void addReceiversInBatch(List<CleverreachReceiver> lot) {

		ReceiverAddBatch request = newReceiverAddBatchRequest(lot);

		ReceiverAddBatchResponse response = (ReceiverAddBatchResponse) perform(request, "receiverAddBatch");

		checkResponseStatusCode(response.getReturn().getStatuscode(), "receiverAddBatch");

	}

	private void updateReceiversInBatch(List<CleverreachReceiver> lot) {

		ReceiverUpdateBatch request = newReceiverUpdateBatchRequest(lot);

		ReceiverUpdateBatchResponse response = (ReceiverUpdateBatchResponse) perform(request, "receiverUpdateBatch");

		checkResponseStatusCode(response.getReturn().getStatuscode(), "receiverUpdateBatch");

	}

	private ReceiverAddBatch newReceiverAddBatchRequest(List<CleverreachReceiver> lot) {

		ReceiverAddBatch request = new ReceiverAddBatch();
		request.setApiKey(apiKey);
		request.setListId(listId);

		for (CleverreachReceiver receiverInLot : lot) {

			ReceiverData receiverData = new ReceiverData();

			receiverData.setEmail(receiverInLot.getEmail());

			ReceiverAttribute receiverDataAtt = new ReceiverAttribute();
			receiverDataAtt.setKey(ATT_FLAG_IMPORT_KEY);
			receiverDataAtt.setValue("1");

			ReceiverAttribute receiverFirstname = new ReceiverAttribute();
			receiverFirstname.setKey(ATT_FIRSTNAME_KEY);
			receiverFirstname.setValue(receiverInLot.getFirstname());

			ReceiverAttribute receiverLastname = new ReceiverAttribute();
			receiverLastname.setKey(ATT_LASTNAME_KEY);
			receiverLastname.setValue(receiverInLot.getLastname());

			receiverData.getReceiverAttributes().addAll(asList(receiverDataAtt, receiverFirstname, receiverLastname));

			request.getSubscriberData().add(receiverData);

		}

		return request;
	}

	private void receiverDelete(String email) {

		ReceiverDelete request = newReceiverDeleteRequest(email);

		ReceiverDeleteResponse response = (ReceiverDeleteResponse) perform(request, "receiverDelete");

		checkResponseStatusCode(response.getReturn().getStatuscode(), "receiverDelete");

	}

	private ReceiverUpdateBatch newReceiverUpdateBatchRequest(List<CleverreachReceiver> lot) {

		ReceiverUpdateBatch request = new ReceiverUpdateBatch();
		request.setApiKey(apiKey);
		request.setListId(listId);

		for (CleverreachReceiver receiverInLot : lot) {

			ReceiverData receiverData = new ReceiverData();

			receiverData.setEmail(receiverInLot.getEmail());

			ReceiverAttribute receiverFirstname = new ReceiverAttribute();
			receiverFirstname.setKey(ATT_FIRSTNAME_KEY);
			receiverFirstname.setValue(receiverInLot.getFirstname());

			ReceiverAttribute receiverLastname = new ReceiverAttribute();
			receiverLastname.setKey(ATT_LASTNAME_KEY);
			receiverLastname.setValue(receiverInLot.getLastname());

			receiverData.getReceiverAttributes().addAll(asList(receiverFirstname, receiverLastname));

			request.getUserData().add(receiverData);

		}

		return request;
	}

	private ReceiverGetPage newReceiverGetPageRequest(Integer page) {
		ReceiverGetPage request = new ReceiverGetPage();

		request.setApiKey(apiKey);
		request.setListId(listId);
		PageFilterSegment pageFilterSegment = new PageFilterSegment();
		pageFilterSegment.setFilter(PARAM_FILER_ALL_VALUE);
		pageFilterSegment.setPage(page);
		request.setPage(pageFilterSegment);
		return request;
	}

	private ReceiverDelete newReceiverDeleteRequest(String email) {
		ReceiverDelete request = new ReceiverDelete();

		request.setApiKey(apiKey);
		request.setEmail(email);

		return request;
	}

	private List<CleverreachReceiver> toResult(ReceiverGetPageResponse response) {

		if (response == null || response.getReturn() == null || isEmpty(response.getReturn().getReceiverData())) {
			return Collections.emptyList();
		}

		List<CleverreachReceiver> results = new ArrayList<CleverreachReceiver>();

		List<ReceiverData> receiverDataList = response.getReturn().getReceiverData();

		for (ReceiverData receiverData : receiverDataList) {
			// add the email and flag returned by the server
			CleverreachReceiver receiverResult = new CleverreachReceiver();

			receiverResult.setEmail(receiverData.getEmail());

			// get receiver attributes
			List<ReceiverAttribute> receiverAttributes = receiverData.getReceiverAttributes();

			Map<String, String> mapKeyValue = toMapKeyValue(receiverAttributes);

			receiverResult.setFlagImport(Objects.equals(ATT_FLAG_IMPORT_VALUE_TRUE,
					mapKeyValue.get(ATT_FLAG_IMPORT_KEY)));
			receiverResult.setFirstname(mapKeyValue.get(ATT_FIRSTNAME_KEY));
			receiverResult.setLastname(mapKeyValue.get(ATT_LASTNAME_KEY));

			results.add(receiverResult);
		}

		return results;
	}

	private Map<String, String> toMapKeyValue(List<ReceiverAttribute> receiverAttributes) {
		Map<String, String> map = new HashMap<String, String>();

		for (ReceiverAttribute receiverAttribute : receiverAttributes) {
			map.put(receiverAttribute.getKey(), receiverAttribute.getValue());
		}

		return map;

	}

	private void checkResponseStatusCode(int statusCode, String method) {
		if (statusCode != 0) {
			throw new GeneralException("The response was for method '" + method + "' not successfull");
		}

	}
}
