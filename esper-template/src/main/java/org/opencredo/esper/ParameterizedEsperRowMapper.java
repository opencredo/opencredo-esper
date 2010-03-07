/*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 3
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/

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
