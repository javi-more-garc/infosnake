/**
 * 
 */
package com.jmg.iic.cleverreach;

import java.util.List;
import java.util.Set;

/**
 * Interface defining services to operate with the Cleverreach's SOAP API
 * 
 * @author Javier Moreno Garcia
 *
 */
public interface CleverreachClient {

	/**
	 * Retrieve all available {@link CleverreachReceiver}s
	 * 
	 * @return
	 */
	List<CleverreachReceiver> getAllReceivers();

	/**
	 * Add new receivers (operation in batch)
	 * 
	 * @param receivers
	 */
	void addReceivers(Set<CleverreachReceiver> receivers);

	/**
	 * Remove the receivers associated to the passed emails
	 * 
	 * @param emails
	 */
	void removeReceivers(Set<String> emails);

	/**
	 * Update receivers (operation in batch)
	 * 
	 * @param receivers
	 */
	void updateReceivers(Set<CleverreachReceiver> receivers);

}
