/**
 * 
 */
package com.jmg.iic.cleverreach;

import java.util.List;
import java.util.Set;

/**
 * @author Javier Moreno Garcia
 *
 */
public interface CleverreachClient {

	List<CleverreachReceiver> getAllReceivers();

	void addReceivers(Set<CleverreachReceiver> receivers);

	void removeReceivers(Set<String> emails);

	void updateReceivers(Set<CleverreachReceiver> receivers);

}
