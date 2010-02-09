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
