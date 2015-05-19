/**
 * 
 */
package com.jmg.iic.core;

import static com.google.common.collect.Sets.difference;
import static com.google.common.collect.Sets.intersection;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.jmg.iic.cleverreach.CleverreachClient;
import com.jmg.iic.cleverreach.CleverreachReceiver;
import com.jmg.iic.infosnake.InfosnakeClient;
import com.jmg.iic.infosnake.InfosnakeUser;

/**
 * Implementation of {@link MatchService}
 * 
 * @author Javier Moreno Garcia
 *
 */
@Service
public class MatchServiceImpl implements MatchService {

	private Logger logger = LoggerFactory.getLogger(MatchServiceImpl.class);

	@Inject
	private CleverreachClient cleverClient;

	@Inject
	private InfosnakeClient userClient;

	@Override
	public void matchValues() {

		// 1 get all users from Infosnake
		List<InfosnakeUser> users = userClient.getAllUsers();

		// 2 get all receivers from Cleverreach
		List<CleverreachReceiver> receivers = cleverClient.getAllReceivers();

		// 3 map both of them using their email
		Map<String, InfosnakeUser> mapUsers = toUsersMap(users);
		Map<String, CleverreachReceiver> mapReceivers = toReceiversMap(receivers);

		Set<String> userEmails = mapUsers.keySet();
		Set<String> receiverEmails = mapReceivers.keySet();

		//
		// 4 users with emails in Infosnake that do not appear in Cleverreach
		// (-> add them as receivers in Cleverreach)
		addReceivers(mapUsers, userEmails, receiverEmails);

		//
		// 5 users in Cleverreach that were imported and whose email does not
		// appear in Infosnake (-> remove them from Cleverreach)
		removeReceivers(mapReceivers, userEmails);

		//
		// 6 users whose emails appear both in Infosnake and Cleverreach (->
		// update them in Cleverreach)
		updateReceivers(mapUsers, mapReceivers, userEmails, receiverEmails);

	}

	//
	// private methods

	private void addReceivers(Map<String, InfosnakeUser> mapUsers, Set<String> userEmails, Set<String> receiverEmails) {

		Set<String> emailsToAdd = difference(userEmails, receiverEmails);
		Set<CleverreachReceiver> receiversToAdd = toList(emailsToAdd, mapUsers);

		if (isEmpty(receiversToAdd)) {
			logger.info("*** No receivers to add to Cleverreach.");
		} else {
			logger.info("*** These receivers need to be added to Cleverreach '{}'.", receiversToAdd);
			cleverClient.addReceivers(receiversToAdd);
		}

	}

	private void removeReceivers(Map<String, CleverreachReceiver> mapReceivers, Set<String> userEmails) {
		// filter those receivers that were not imported
		Set<String> importedReceiverEmails = filterNotImported(mapReceivers).keySet();
		Set<String> emailsToRemove = difference(importedReceiverEmails, userEmails);

		if (isEmpty(emailsToRemove)) {
			logger.info("*** No receivers to be removed from Cleverreach.");
		} else {
			logger.info("*** Receivers with these emails need to be removed from Cleverreach '{}'.", emailsToRemove);
			cleverClient.removeReceivers(emailsToRemove);
		}

	}

	private void updateReceivers(Map<String, InfosnakeUser> mapUsers, Map<String, CleverreachReceiver> mapReceivers,
			Set<String> userEmails, Set<String> receiverEmails) {

		Set<String> emailsToUpdate = intersection(userEmails, receiverEmails);

		Set<CleverreachReceiver> receiversToUpdate = new HashSet<CleverreachReceiver>();

		for (String email : emailsToUpdate) {

			// get user in Infosnake
			InfosnakeUser infosnakeUser = mapUsers.get(email);

			// get receiver in Cleverreach
			CleverreachReceiver cleverreahReceiver = mapReceivers.get(email);

			// if there is any difference
			if (!infosnakeUser.getFirstname().equals(cleverreahReceiver.getFirstname())
					|| !infosnakeUser.getLastname().equals(cleverreahReceiver.getLastname())) {

				CleverreachReceiver toUpate = new CleverreachReceiver();

				toUpate.setEmail(cleverreahReceiver.getEmail());
				toUpate.setFirstname(infosnakeUser.getFirstname());
				toUpate.setLastname(infosnakeUser.getLastname());

				receiversToUpdate.add(toUpate);

			}

		}

		if (isEmpty(receiversToUpdate)) {
			logger.info("*** No receivers to be updated in Cleverreach.");
		} else {
			logger.info("*** These receivers need to be updated in Cleverreach '{}'.", receiversToUpdate);
			cleverClient.updateReceivers(receiversToUpdate);
		}

	}

	private Set<CleverreachReceiver> toList(Set<String> emailsToUpdateInCleverreach, Map<String, InfosnakeUser> mapUsers) {

		Set<CleverreachReceiver> result = new HashSet<CleverreachReceiver>();

		for (String email : emailsToUpdateInCleverreach) {

			InfosnakeUser user = mapUsers.get(email);

			if (user != null) {

				CleverreachReceiver receiver = new CleverreachReceiver();

				receiver.setEmail(email);
				receiver.setFirstname(user.getFirstname());
				receiver.setLastname(user.getLastname());

				result.add(receiver);
			}

		}

		return result;
	}

	private Map<String, CleverreachReceiver> filterNotImported(Map<String, CleverreachReceiver> mapReceivers) {

		Map<String, CleverreachReceiver> result = new HashMap<String, CleverreachReceiver>();

		for (Entry<String, CleverreachReceiver> entry : mapReceivers.entrySet()) {

			CleverreachReceiver value = entry.getValue();

			// returning only those imported (flag import (1)
			if (value.getFlagImport()) {
				result.put(entry.getKey(), value);
			}

		}
		return result;
	}

	private Map<String, CleverreachReceiver> toReceiversMap(List<CleverreachReceiver> receivers) {

		return Maps.uniqueIndex(receivers, new Function<CleverreachReceiver, String>() {
			public String apply(CleverreachReceiver from) {
				return from.getEmail();
			}
		});

	}

	private Map<String, InfosnakeUser> toUsersMap(List<InfosnakeUser> users) {

		return Maps.uniqueIndex(users, new Function<InfosnakeUser, String>() {
			public String apply(InfosnakeUser from) {
				return from.getEmail();
			}
		});

	}

}
