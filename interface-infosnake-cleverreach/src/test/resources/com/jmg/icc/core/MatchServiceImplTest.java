/**
 * 
 */
package com.jmg.icc.core;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.jmg.iic.cleverreach.CleverreachClient;
import com.jmg.iic.cleverreach.CleverreachReceiver;
import com.jmg.iic.core.MatchService;
import com.jmg.iic.core.MatchServiceImpl;
import com.jmg.iic.infosnake.InfosnakeClient;
import com.jmg.iic.infosnake.InfosnakeUser;

/**
 * Unit testing for {@link MatchServiceImpl}
 * 
 * @author Javier Moreno Garcia
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class MatchServiceImplTest {

	@Mock
	private CleverreachClient mockCleverClient;

	@Mock
	private InfosnakeClient mockUserClient;

	@InjectMocks
	private MatchService matchService = new MatchServiceImpl();

	@Captor
	private ArgumentCaptor<Set<CleverreachReceiver>> argumentReceiver;

	@Captor
	private ArgumentCaptor<Set<String>> argumentEmail;

	@Test
	public void testMatchValuesNothingToDo() {

		// prepare calls
		when(mockUserClient.getAllUsers()).thenReturn(getUsers());

		when(mockCleverClient.getAllReceivers()).thenReturn(getReceivers());

		// perform business
		matchService.matchValues();

		// no call to add
		verify(mockCleverClient, never()).addReceivers(Mockito.anySetOf(CleverreachReceiver.class));

		// no call to remove
		verify(mockCleverClient, never()).removeReceivers(Mockito.anySetOf(String.class));

		// no call to update
		verify(mockCleverClient, never()).updateReceivers(Mockito.anySetOf(CleverreachReceiver.class));
	}

	@Test
	public void testMatchValuesAddOne() {

		List<InfosnakeUser> users = new ArrayList<InfosnakeUser>();

		InfosnakeUser newUser = newUser("user4@email.com", "f4", "l4");

		users.add(newUser);
		users.addAll(getUsers());

		// prepare calls
		when(mockUserClient.getAllUsers()).thenReturn(users);

		when(mockCleverClient.getAllReceivers()).thenReturn(getReceivers());

		// perform business
		matchService.matchValues();

		// one to add
		verify(mockCleverClient).addReceivers(argumentReceiver.capture());
		Set<CleverreachReceiver> toAdds = argumentReceiver.getValue();

		assertThat(toAdds, hasSize(1));
		CleverreachReceiver toAdd = toAdds.iterator().next();

		verifyReceiver(toAdd, "user4@email.com", "f4", "l4", null);

		// no call to remove
		verify(mockCleverClient, never()).removeReceivers(Mockito.anySetOf(String.class));

		// no call to update
		verify(mockCleverClient, never()).updateReceivers(Mockito.anySetOf(CleverreachReceiver.class));

	}

	@Test
	public void testMatchValuesRemoveTwo() {

		// prepare calls
		when(mockUserClient.getAllUsers()).thenReturn(new ArrayList<InfosnakeUser>());

		when(mockCleverClient.getAllReceivers()).thenReturn(getReceivers());

		// perform business
		matchService.matchValues();

		// no call to add
		verify(mockCleverClient, never()).addReceivers(Mockito.anySetOf(CleverreachReceiver.class));

		// two to remove
		verify(mockCleverClient).removeReceivers(argumentEmail.capture());
		Set<String> toRemoves = argumentEmail.getValue();

		assertThat(toRemoves, containsInAnyOrder("user1@email.com", "user3@email.com"));

		// no call to update
		verify(mockCleverClient, never()).updateReceivers(Mockito.anySetOf(CleverreachReceiver.class));

	}

	@Test
	public void testMatchValuesUpdateTwo() {

		List<InfosnakeUser> users = getUsers();
		InfosnakeUser userToUpdate1 = users.get(0);

		userToUpdate1.setFirstname("f1Up");
		userToUpdate1.setLastname("l1Up");

		InfosnakeUser userToUpdate2 = users.get(1);

		userToUpdate2.setFirstname("f2Up");
		userToUpdate2.setLastname("l2Up");

		// prepare calls
		when(mockUserClient.getAllUsers()).thenReturn(users);

		when(mockCleverClient.getAllReceivers()).thenReturn(getReceivers());

		// perform business
		matchService.matchValues();

		// no call to add
		verify(mockCleverClient, never()).addReceivers(Mockito.anySetOf(CleverreachReceiver.class));

		// no call to remove
		verify(mockCleverClient, never()).removeReceivers(Mockito.anySetOf(String.class));

		// one to update
		verify(mockCleverClient).updateReceivers(argumentReceiver.capture());
		Set<CleverreachReceiver> toUpdates = argumentReceiver.getValue();

		assertThat(toUpdates, hasSize(2));

		for (CleverreachReceiver toUpdate : toUpdates) {
			if ("user1@email.com".equals(toUpdate.getEmail())) {
				verifyReceiver(toUpdate, "user1@email.com", "f1Up", "l1Up", null);
			} else {

				verifyReceiver(toUpdate, "user2@email.com", "f2Up", "l2Up", null);
			}
		}

	}

	private void verifyReceiver(CleverreachReceiver receiver, String email, String firstname, String lastname,
			Boolean flag) {
		assertThat(receiver.getEmail(), is(email));
		assertThat(receiver.getFirstname(), is(firstname));
		assertThat(receiver.getLastname(), is(lastname));
		assertThat(receiver.getFlagImport(), is(flag));
	}

	//
	// private methods

	private List<InfosnakeUser> getUsers() {
		InfosnakeUser user1 = newUser("user1@email.com", "f1", "l1");
		InfosnakeUser user2 = newUser("user2@email.com", "f2", "l2");
		InfosnakeUser user3 = newUser("user3@email.com", "f3", "l3");

		return asList(user1, user2, user3);
	}

	private List<CleverreachReceiver> getReceivers() {
		CleverreachReceiver receiver1 = newReceiver("user1@email.com", "f1", "l1", true);
		CleverreachReceiver receiver2 = newReceiver("user2@email.com", "f2", "l2", false);
		CleverreachReceiver receiver3 = newReceiver("user3@email.com", "f3", "l3", true);

		return asList(receiver1, receiver2, receiver3);
	}

	private InfosnakeUser newUser(String email, String firstname, String lastname) {
		InfosnakeUser user1 = new InfosnakeUser();
		user1.setEmail(email);
		user1.setFirstname(firstname);
		user1.setLastname(lastname);
		return user1;
	}

	private CleverreachReceiver newReceiver(String email, String firstname, String lastname, boolean a) {

		CleverreachReceiver receiver = new CleverreachReceiver();

		receiver.setEmail(email);
		receiver.setFirstname(firstname);
		receiver.setLastname(lastname);
		receiver.setFlagImport(a);

		return receiver;
	}
}
