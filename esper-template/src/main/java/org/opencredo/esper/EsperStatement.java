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

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

public class EsperStatement implements EsperStatementOperations {
    private String epl;
    private EPStatement epStatement;
    private Set<UpdateListener> listeners = new LinkedHashSet<UpdateListener>();
	private Object subscriber;

    public EsperStatement(String epl) {
        this.epl = epl;
    }

    public String getEPL(){
        return epl;
    }

    public void setListeners(Set<UpdateListener> listeners) {
        this.listeners = listeners;
    	
        this.refreshEPStatmentListeners();
    }
    
    public void setSubscriber(Object subscriber) {
    	this.subscriber = subscriber;
    }
    
    public Set<UpdateListener> getListeners() {
    	return this.listeners;
    }
    
    public void addListener(UpdateListener listener) {
        listeners.add(listener);
        
        this.refreshEPStatmentListeners();

        this.addEPStatementListener(listener);
    }
    
    private void refreshEPStatmentListeners() {
    	for (UpdateListener listener : this.listeners) {
    		this.addEPStatementListener(listener);
    	}
    }
    
    private void addEPStatementListener(UpdateListener listener) {
    	if (this.subscriber == null) {
	    	if (epStatement != null) {
	            epStatement.addListener(listener);
	        }
    	}
    }

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
		
		/*
	    Replacing code that looks like:
	    	
	    	SafeIterator<EventBean> safeIter = statement.safeIterator();
	    try {
	      for (;safeIter.hasNext();) {
	         // .. process event ..
	         EventBean event = safeIter.next();
	         System.out.println("avg:" + event.get("avgPrice");
	      }
	    }
	    finally {
	      safeIter.close();	// Note: safe iterators must be closed
	    }
	    */
		
		// TODO Auto-generated method stub
		return null;
	}

	public List<Map<String, Object>> concurrentSafeQueryForList() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, Object> concurrentSafeQueryForMap() {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T concurrentSafeQueryForObject(ParameterizedEsperRowMapper<T> rm) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> List<T> concurrentUnsafeQafeQuery(
			ParameterizedEsperRowMapper<T> rm) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Map<String, Object>> concurrentUnsafeQueryForList() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, Object> concurrentUnsafeQueryForMap() {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T concurrentUnsafeQueryForObject(
			ParameterizedEsperRowMapper<T> rm) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
