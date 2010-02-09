/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opencredo.esper;

import java.util.List;

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
	<T> List<T> concurrentUnsafeQuery(ParameterizedEsperRowMapper<T> rm);

}
