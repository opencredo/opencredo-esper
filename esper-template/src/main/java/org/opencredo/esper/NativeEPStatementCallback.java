package org.opencredo.esper;

import com.espertech.esper.client.EPStatement;

/**
 * Provides a callback hook in order to expose the underlying
 * Esper resources, such as the {@link EPStatement}.
 * 
 * @author Russ Miles (russ.miles@opencredo.com)
 */
public interface NativeEPStatementCallback {
	/**
	 * Passes the native EPStatement and registered EPL filter query
	 * to the implementor.
	 * 
	 * @param nativeStatement the native EPStatement
	 * @param epl the epl filter query registered with this statement
	 */
	public void executeWithEPStatement(EPStatement nativeStatement, String epl);
}
