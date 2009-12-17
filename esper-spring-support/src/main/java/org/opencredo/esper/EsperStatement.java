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

    public void setListeners(UpdateListener... listeners) {
        for (UpdateListener listener : listeners) {
            addListener(listener);
        }
    }
    public void addListener(UpdateListener listener) {
        listeners.add(listener);
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
