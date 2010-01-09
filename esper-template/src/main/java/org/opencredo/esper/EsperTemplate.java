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

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPException;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UnmatchedListener;

/**
 * The main workhorse of Esper. The template is configured with a set of
 * statements that query the flow of events. Each statement is then associated
 * with a number of listeners who will be notified when there is a result from
 * the statement.
 * 
 * Once setup the template is then used to inform esper of any events of
 * interest by calling sendEvent(Object).
 * 
 * @author Russ Miles (russ.miles@opencredo.com)
 * 
 */
public final class EsperTemplate implements BeanNameAware, InitializingBean,
		DisposableBean {

	private EPServiceProvider epServiceProvider;
	private EPRuntime epRuntime;
	private String name;
	private Set<EsperStatement> statements = new LinkedHashSet<EsperStatement>();
	private Resource configuration;
	private UnmatchedListener unmatchedListener;

	/**
	 * Add a collection of {@link EsperStatement} to the template.
	 * 
	 * @param statementBeans
	 */
	public void setStatements(Set<EsperStatement> statements) {
		this.statements = statements;
	}

	/**
	 * Set the location of the XML Esper configuration resource.
	 * 
	 * @param configurationResource
	 */
	public void setConfiguration(Resource configuration) {
		this.configuration = configuration;
	}
	
	/**
	 * Specify the listener that should be notified of any unmatched
	 * events.
	 * 
	 * @param unmatchedListener The listener that is notified of events that are not matched
	 */
	public void setUnmatchedListener(UnmatchedListener unmatchedListener) {
		this.unmatchedListener = unmatchedListener;
	}

	/**
	 * Retrieve the configured esper runtime.
	 * 
	 * @return The current esper runtime
	 */
	public EPRuntime getEpRuntime() {
		return epRuntime;
	}

	public Set<EsperStatement> getStatements() {
		return this.statements;
	}

	/**
	 * Adds an {@link EsperStatement} composite to the template.
	 * 
	 * @param statement
	 *            The EsperStatement to add to the template.
	 */
	public void addStatement(EsperStatement statement) {
		statements.add(statement);
	}

	/**
	 * Instructs the template to send an event to Esper. Events are then used to
	 * satisfy statements, which then alert listeners.
	 * 
	 * @param event
	 *            The event that Esper is to be informed of.
	 */
	public void sendEvent(Object event) {
		epRuntime.sendEvent(event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.BeanNameAware#setBeanName(java.lang
	 * .String)
	 */
	public void setBeanName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	/**
	 * Initializes the Esper service provider with the provided statements and
	 * associated listeners.
	 * 
	 * The provider is giving a unique name that is based on the bean name.
	 * @throws IOException 
	 * @throws EPException 
	 */
	private void setupEsper() throws EPException, IOException {
		configureEPServiceProvider();
		epRuntime = epServiceProvider.getEPRuntime();
		if (this.unmatchedListener != null) {
			epRuntime.setUnmatchedListener(unmatchedListener);
		}
		setupEPStatements();
	}

	/**
	 * Add the appropriate statements to the esper runtime.
	 */
	private void setupEPStatements() {
		for (EsperStatement statement : statements) {
			EPStatement epStatement = epServiceProvider.getEPAdministrator()
					.createEPL(statement.getEPL());
			statement.setEPStatement(epStatement);
		}
	}

	/**
	 * Configure the Esper Service Provider to create the appropriate Esper
	 * Runtime.
	 * @throws IOException 
	 * @throws EPException 
	 */
	private void configureEPServiceProvider() throws EPException, IOException {
		if (this.configuration != null && this.configuration.exists()) {
			Configuration configuration = new Configuration();
			configuration = configuration.configure(this.configuration
					.getFile());
			epServiceProvider = EPServiceProviderManager.getProvider(name, configuration);
		} else {
			epServiceProvider = EPServiceProviderManager.getProvider(name);
		}
	}

	/**
	 * Tidies up the Esper service provider, which in turn releases any
	 * resources being used by Esper.
	 */
	private void cleanup() {
		epServiceProvider.destroy();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		this.setupEsper();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	public void destroy() throws Exception {
		this.cleanup();
	}
}
