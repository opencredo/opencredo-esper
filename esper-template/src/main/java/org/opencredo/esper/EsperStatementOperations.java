package org.opencredo.esper;

import java.util.List;
import java.util.Map;

/**
 * Esper support the capability to 'pull' query filter results from a statement.
 * This interface expresses the simplified Esper 'pull' query operations.
 * 
 * For more information on 'pull' operations, see {@link http
 * ://esper.codehaus.org
 * /esper-3.3.0/doc/reference/en/html/api.html#api-receive-results}
 * 
 * @author Russ Miles (russ.miles@opencredo.com)
 * @see org.opencredo.esper.EsperStatement
 */
public interface EsperStatementOperations {

	/**
	 * Performs a concurrency safe 'pull' to retrieve query filtered results
	 * from the esper statement.
	 * 
	 * Be aware that this operation will lock the underlying esper statement
	 * resources while executing.
	 * 
	 * @param rm
	 *            the {@link ParameterizedEsperRowMapper} to use for result
	 *            mapping
	 * @return the single mapped object
	 */
	<T> T concurrentSafeQueryForObject(ParameterizedEsperRowMapper<T> rm);

	/**
	 * Performs a concurrency safe 'pull' to retrieve a query filtered result
	 * from the esper statement. That result is then automatically used to
	 * populate the returned {@link Map}.
	 * 
	 * @return A map containing the a single result from the statement
	 */
	Map<String, Object> concurrentSafeQueryForMap();

	/**
	 * Performs a concurrency safe 'pull' to retrieve query filtered results
	 * from the esper statement. Those results are then automatically used to
	 * populate the returned {@link List} of {@link Map} objects.
	 * 
	 * @return a list containing all of the individual result maps
	 */
	List<Map<String, Object>> concurrentSafeQueryForList();

	/**
	 * Performs a concurrency safe 'pull' to retrieve query filtered results
	 * from the esper statement. The supplied
	 * {@link ParameterizedEsperRowMapper} is then used to populate the objects
	 * that are then collected together and returned as a {@link List}.
	 * 
	 * @param rm
	 *            the {@link ParameterizedEsperRowMapper} to use for result
	 *            mapping
	 * @return a list containing all the objects mapped from the query results
	 */
	<T> List<T> concurrentSafeQuery(ParameterizedEsperRowMapper<T> rm);

	/**
	 * Performs a concurrency unsafe 'pull' (i.e. should only be used in a
	 * single threaded environment) to retrieve a query filtered result from the
	 * esper statement.
	 * 
	 * The supplied {@link ParameterizedEsperRowMapper} is then used to populate
	 * the object that is returned.
	 * 
	 * @param rm
	 *            the {@link ParameterizedEsperRowMapper} to use for result
	 *            mapping
	 * @return the mapped object
	 */
	<T> T concurrentUnsafeQueryForObject(ParameterizedEsperRowMapper<T> rm);

	/**
	 * Performs a concurrency unsafe 'pull' (i.e. should only be used in a
	 * single threaded environment) to retrieve a query filtered result from the
	 * esper statement. That result is then automatically used to populate the
	 * returned {@link Map}.
	 * 
	 * @return A map containing the a single result from the statement
	 */
	Map<String, Object> concurrentUnsafeQueryForMap();

	/**
	 * Performs a concurrency unsafe 'pull' (i.e. should only be used in a
	 * single threaded environment) to retrieve query filtered results from the
	 * esper statement. Those results are then automatically used to populate
	 * the returned {@link List} of {@link Map} objects.
	 * 
	 * @return a list containing all of the individual result maps
	 */
	List<Map<String, Object>> concurrentUnsafeQueryForList();

	/**
	 * Performs a concurrency unsafe 'pull' (i.e. should only be used in a
	 * single threaded environment) to retrieve query filtered results from the
	 * esper statement. The supplied {@link ParameterizedEsperRowMapper} is then
	 * used to populate the objects that are then collected together and
	 * returned as a {@link List}.
	 * 
	 * @param rm
	 *            the {@link ParameterizedEsperRowMapper} to use for result
	 *            mapping
	 * @return a list containing all the objects mapped from the query results
	 */
	<T> List<T> concurrentUnsafeQafeQuery(ParameterizedEsperRowMapper<T> rm);

}
