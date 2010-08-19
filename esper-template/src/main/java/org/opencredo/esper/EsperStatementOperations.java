/*
 * OpenCredo-Esper - simplifies adopting Esper in Java applications. 
 * Copyright (C) 2010  OpenCredo Ltd.
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
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
