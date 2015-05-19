package com.jmg.iic.core;

/**
 * Interface defining services to match information between Infosnake and
 * Cleverreach
 * 
 * @author Javier Moreno Garcia
 *
 */
public interface MatchService {

	/**
	 * This is service that performs the matching. The steps are as follows:
	 * 
	 * 1 retrieve all users from Infosnake <br/>
	 * 
	 * 2 retrieve all receivers from Cleverreach <br/>
	 * 
	 * 3 add in Cleverreach those users with emails appearing in Infosnake but
	 * not in Cleverreach <br/>
	 * 
	 * 4 remove from Cleverreach those users that were imported and whose email
	 * does not appear in Infosnake <br/>
	 * 
	 * 6 update in Cleverreach those users whose emails appear both in Infosnake
	 * and Cleverreach <br/>
	 */
	public void matchValues();

}