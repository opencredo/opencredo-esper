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
