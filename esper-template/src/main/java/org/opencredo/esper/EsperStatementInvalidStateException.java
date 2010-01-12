package org.opencredo.esper;

/**
 * Runtime exception that is raised when an attempt to execute
 * an operation on an {@link EsperStatement} fails due to the state
 * of the statement resource.
 * 
 * @author Russ Miles (russ.miles@opencredo.com)
 *
 */
@SuppressWarnings("serial")
public class EsperStatementInvalidStateException extends RuntimeException {

	public EsperStatementInvalidStateException(String message) {
		super(message);
	}
}
