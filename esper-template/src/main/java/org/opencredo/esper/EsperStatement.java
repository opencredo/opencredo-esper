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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EPStatementState;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.SafeIterator;
import com.espertech.esper.client.UpdateListener;

/**
 * Implements a set of convenient Template operations around
 * a native Esper Statement.
 * 
 * Support both the push (via listener or subscriber) or pull
 * (via the template methods) operations to retrieve results
 * from the associated statement.
 * 
 * For more information on push and pull operations on esper statements,
 * see {@link http://esper.codehaus.org/esper-3.3.0/doc/reference/en/html/api.html#api-receive-results}
 * 
 * @author Russ Miles (russ.miles@opencredo.com)
 */
public class EsperStatement implements EsperStatementOperations {
	private final static Logger LOG = LoggerFactory.getLogger(EsperStatement.class);
	
	private String epl;
	private EPStatement epStatement;
	private Set<UpdateListener> listeners = new LinkedHashSet<UpdateListener>();
	private Object subscriber;

	public EsperStatement(String epl) {
		this.epl = epl;
	}

	public String getEPL() {
		return epl;
	}
	
	public EPStatementState getState() {
		return this.epStatement.getState();
	}
	
	/**
	 * Starts events being collated according to the statement's filter query
	 */
	public void start() {
		LOG.info("Esper statement being started");
		
		this.epStatement.start();
		
		LOG.info("Esper statement started");
	}
	
	/**
	 * Stops the underlying native statement from applying its filter query.
	 */
	public void stop() {
		LOG.info("Esper statement being stopped");
		
		this.epStatement.stop();
		
		LOG.info("Esper statement stopped");
	}
	
	/**
	 * Provides a mechanism by which to access the underlying esper API
	 * 
	 * @param callback used to pass access to the underlying esper API resources
	 */
	public void doWithNativeEPStatement(NativeEPStatementCallback callback) {
		callback.executeWithEPStatement(this.epStatement, this.epl);
	}

	public void setListeners(Set<UpdateListener> listeners) {
		this.listeners = listeners;

		this.refreshEPStatmentListeners();
	}
	
	public Set<UpdateListener> getListeners() {
		return this.listeners;
	}

	public void setSubscriber(Object subscriber) {
		this.subscriber = subscriber;
	}

	/**
	 * Adds an {@link UpdateListener) to the statement to support
	 * the 'push' mode of retrieving results.
	 * 
	 * @param listener The listener to be invoked when appropriate results according to the EPL filter query.
	 */
	public void addListener(UpdateListener listener) {
		listeners.add(listener);

		this.refreshEPStatmentListeners();

		this.addEPStatementListener(listener);
	}

	/**
	 * Refreshes the listeners associated with this statement.
	 */
	private void refreshEPStatmentListeners() {
		for (UpdateListener listener : this.listeners) {
			this.addEPStatementListener(listener);
		}
	}

	/**
	 * Adds an listener to the underlying native EPStatement.
	 * 
	 * @param listener the listener to add
	 */
	private void addEPStatementListener(UpdateListener listener) {
		if (this.subscriber == null) {
			if (epStatement != null) {
				epStatement.addListener(listener);
			}
		}
	}

	/**
	 * Sets the native Esper statement. Typically created
	 * by an Esper Template.
	 * 
	 * @param epStatement the underlying native Esper statement
	 * @see org.opencredo.esper.EsperTemplate
	 */
	void setEPStatement(EPStatement epStatement) {
		this.epStatement = epStatement;
		if (this.subscriber != null) {
			epStatement.setSubscriber(this.subscriber);
		} else {
			for (UpdateListener listener : listeners) {
				epStatement.addListener(listener);
			}
		}
	}

	public <T> List<T> concurrentSafeQuery(ParameterizedEsperRowMapper<T> rm) {
		LOG.info("Concurrent safe query being executed");
		
		if (epStatement.isStopped() || epStatement.isDestroyed()) {
			LOG.error("Concurrent safe query was attempted when the statement was stopped or destroyed");
			throw new EsperStatementInvalidStateException("Attempted to execute a concurrent safe query when esper statement resource had state of " + epStatement.getState());
		}
		
		SafeIterator<EventBean> safeIter = this.epStatement.safeIterator();

		List<T> objectList = new ArrayList<T>();
		try {
			for (; safeIter.hasNext();) {
				EventBean event = safeIter.next();
				objectList.add(rm.mapRow(event));
			}
		} finally {
			safeIter.close();
		}
		
		LOG.info("Concurrent safe query was completed");
		return objectList;
	}

	public <T> T concurrentSafeQueryForObject(ParameterizedEsperRowMapper<T> rm) {
		LOG.info("Concurrent safe query for object being executed");
		
		if (epStatement.isStopped() || epStatement.isDestroyed()) {
			LOG.error("Concurrent safe query for object was attempted when the statement was stopped or destroyed");
			throw new EsperStatementInvalidStateException("Attempted to execute a concurrent safe query for object when esper statement resource had state of " + epStatement.getState());
		}
		
		SafeIterator<EventBean> safeIter = this.epStatement.safeIterator();
		
		T result = null;
		try {
			// Only retrieve the last result
			while (safeIter.hasNext()) {
				EventBean event = safeIter.next();
				if (!safeIter.hasNext()) {
					result = rm.mapRow(event);
				}

			}
		} finally {
			safeIter.close();
		}

		LOG.info("Concurrent safe query for object was completed");
		return result;
	}

	public <T> List<T> concurrentUnsafeQuery(ParameterizedEsperRowMapper<T> rm) {
		LOG.info("Concurrent unsafe query being executed");
		
		if (epStatement.isStopped() || epStatement.isDestroyed()) {
			LOG.error("Concurrent unsafe query was attempted when the statement was stopped or destroyed");
			throw new EsperStatementInvalidStateException("Attempted to execute a concurrent unsafe query when esper statement resource had state of " + epStatement.getState());
		}
		
		Iterator<EventBean> safeIter = this.epStatement.iterator();

		List<T> objectList = new ArrayList<T>();
		for (; safeIter.hasNext();) {
			EventBean event = safeIter.next();
			objectList.add(rm.mapRow(event));
		}

		LOG.info("Concurrent unsafe query was completed");
		return objectList;
	}

	public <T> T concurrentUnsafeQueryForObject(
			ParameterizedEsperRowMapper<T> rm) {
		LOG.info("Concurrent unsafe query for object being executed");
		
		if (epStatement.isStopped() || epStatement.isDestroyed()) {
			LOG.error("Concurrent unsafe query for object was attempted when the statement was stopped or destroyed");
			throw new EsperStatementInvalidStateException("Attempted to execute a concurrent unsafe query for object when esper statement resource had state of " + epStatement.getState());
		}
		
		Iterator<EventBean> safeIter = this.epStatement.iterator();

		T result = null;

		// Only retrieve the last result
		while (safeIter.hasNext()) {
			EventBean event = safeIter.next();
			if (!safeIter.hasNext()) {
				result = rm.mapRow(event);
			}

		}

		LOG.info("Concurrent unsafe query for object was completed");
		return result;
	}

}
