/**
 * 
 */
package com.jmg.iic.core;

/**
 * Generalization of {@link RuntimeException} to inform about general problems
 * (e.g. SOAP response is not successful)
 * 
 * @author Javier Moreno Garcia
 *
 */
public class GeneralException extends RuntimeException {

	private static final long serialVersionUID = -1160730627714984714L;

	/**
	 * Constructor GeneralException
	 * 
	 * @param msg
	 */
	public GeneralException(String msg) {
		super(msg);
	}

	/**
	 * Constructor GeneralException
	 * 
	 * @param msg
	 * @param t
	 */
	public GeneralException(String msg, Throwable t) {
		super(msg, t);
	}

}
