package org.opencredo.esper;

import com.espertech.esper.client.EventBean;

/**
 * Provides a callback to map a set of results from Esper, in a notional row.
 * Implementations will then use their own strategy to implement the callbck.
 * 
 * Uses Java 5 covariant return types to override the return type of the
 * {@link #mapRow} method to be the type parameter <code>T</code>. Created in
 * the style of the Sprng Framework's
 * {@link org.springframework.jdbc.core.SimpleParameterizedRowMapper}.
 * 
 * @author Russ Miles (russ.miles@opencredo.com)
 * @see org.opencredo.esper.EsperStatementOperations
 */
public interface ParameterizedEsperRowMapper<T> {

	/**
	 * Implementations should return the object representation of the current
	 * row as supplied by the {@link EventBean}.
	 * 
	 * @param eventBean
	 * @return 
	 */
	T mapRow(EventBean eventBean);
}
