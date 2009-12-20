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
