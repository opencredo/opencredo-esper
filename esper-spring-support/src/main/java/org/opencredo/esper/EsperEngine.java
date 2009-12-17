package org.opencredo.esper;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.BeanNameAware;

import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

/**
 * @author Russ Miles (russell.miles@opencredo.com)
 *
 */
public class EsperEngine implements BeanNameAware {
    private EPServiceProvider epServiceProvider;
    private EPRuntime epRuntime;
    private String name;
    private Set<EsperStatement> statementBeans = new LinkedHashSet<EsperStatement>();

    public void setStatements(EsperStatement... statementBeans) {
		for (EsperStatement statementBean : statementBeans) {
		    addStatement(statementBean);
		}
    }

    public void addStatement(EsperStatement statementBean) {
    	statementBeans.add(statementBean);
    }

    public void sendEvent(Object event) {
    	epRuntime.sendEvent(event);
    }

    public void setBeanName(String name) {
    	this.name = name;
    }
    
    @PostConstruct
    public void setupEsper() {
		epServiceProvider = EPServiceProviderManager.getProvider(name);
		epRuntime = epServiceProvider.getEPRuntime();
		for (EsperStatement statementBean : statementBeans) {
		    EPStatement epStatement = epServiceProvider.getEPAdministrator().createEPL(statementBean.getEPL());
		    statementBean.setEPStatement(epStatement);
		}
    }
    
    @PreDestroy
    public void cleanup() {
    	epServiceProvider.destroy();
    }
}
