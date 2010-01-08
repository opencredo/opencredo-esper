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
import java.util.Set;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

public class EsperStatement {
    private String epl;
    private EPStatement epStatement;
    private Set<UpdateListener> listeners = new LinkedHashSet<UpdateListener>();

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
    	if (epStatement != null) {
            epStatement.addListener(listener);
        }
    }

    void setEPStatement(EPStatement epStatement) {
        this.epStatement = epStatement;
        for (UpdateListener listener : listeners) {
            epStatement.addListener(listener);
        }
    }
    
}
