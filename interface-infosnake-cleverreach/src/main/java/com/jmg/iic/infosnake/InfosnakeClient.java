/**
 * 
 */
package com.jmg.iic.infosnake;

import java.util.List;

/**
 * Interface defining services to operate with the Cleverreach's SOAP API
 * 
 * @author Javier Moreno Garcia
 *
 */
public interface InfosnakeClient {

	/**
	 * Retrieve all available {@link InfosnakeUser}s
	 * 
	 * @return
	 */
	List<InfosnakeUser> getAllUsers();

}
