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

import java.util.Set;

import org.springframework.core.io.Resource;

import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.UnmatchedListener;

/**
 * Specifies the operations exposed by an Esper Template operations.
 * 
 * @author Russ Miles (russ.miles@opencredo.com)
 * 
 */
public interface EsperTemplateOperations {

	/**
	 * Sets up and allocated all resources associated with this template prior
	 * to its use. Initializes the Esper service provider with the provided
	 * statements and associated listeners.
	 * 
	 * Works together with the cleanup() operation, which in turn frees up the
	 * resources used by this template.
	 * 
	 * @throws InvalidEsperConfigurationException
	 *             thrown if any configuration errors have been made
	 */
	public void initialize() throws InvalidEsperConfigurationException;

	/**
	 * Deallocated all resources associated with this template when it is
	 * finished with. Works together with the initialize() operation.
	 * 
	 */
	public void cleanup();

	/**
	 * Sets the name used to identify this template and its resources within
	 * Esper.
	 * 
	 * @param name
	 *            The name associated with this template
	 */
	public void setName(String name);

	/**
	 * Add a collection of {@link EsperStatement} to the template.
	 * 
	 * @param statementBeans
	 */
	public void setStatements(Set<EsperStatement> statements);

	/**
	 * Set the location of the XML Esper configuration resource.
	 * 
	 * @param configurationResource
	 */
	public void setConfiguration(Resource configuration);

	/**
	 * Specify the listener that should be notified of any unmatched events.
	 * 
	 * @param unmatchedListener
	 *            The listener that is notified of events that are not matched
	 */
	public void setUnmatchedListener(UnmatchedListener unmatchedListener);

	/**
	 * Retrieve the configured esper native runtime.
	 * 
	 * @return The current esper native runtime
	 */
	public EPRuntime getEsperNativeRuntime();

	/**
	 * Return the currently configured and registered set of
	 * {@link EsperStatement} objects.
	 * 
	 * @return a set of configured {@link EsperStatement} objects.
	 */
	public Set<EsperStatement> getStatements();

	/**
	 * Adds an {@link EsperStatement} to the template.
	 * 
	 * @param statement
	 *            The EsperStatement to add to the template.
	 */
	public void addStatement(EsperStatement statement);

	/**
	 * Instructs the template to send an event to Esper. Events are then used to
	 * satisfy statements, which then alert listeners.
	 * 
	 * @param event
	 *            The event that Esper is to be informed of.
	 *           
	 * @throws InvalidEsperConfigurationException
	 *             thrown if any runtime configuration problems occur
	 */
	public void sendEvent(Object event) throws InvalidEsperConfigurationException;
}
