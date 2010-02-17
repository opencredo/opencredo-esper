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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * <p/>
 * Once setup the template is then used to inform esper of any events of
 * interest by calling sendEvent(Object).
 *
 * @author Russ Miles (russ.miles@opencredo.com)
 * @author Jonas Partner (jonas.partner@opencredo.com)
 */
public class EsperTemplate implements EsperTemplateOperations {
    private final static Logger LOG = LoggerFactory.getLogger(EsperTemplate.class);

    private EPServiceProvider epServiceProvider;
    private EPRuntime epRuntime;
    private String name;
    private Set<EsperStatement> statements = new LinkedHashSet<EsperStatement>();
    private Resource configuration;
    private UnmatchedListener unmatchedListener;
    private volatile boolean initialised = false;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setStatements(Set<EsperStatement> statements) {
        this.statements = statements;
    }

    public void setConfiguration(Resource configuration) {
        this.configuration = configuration;
    }

    public void setUnmatchedListener(UnmatchedListener unmatchedListener) {
        this.unmatchedListener = unmatchedListener;
    }

    public EPRuntime getEsperNativeRuntime() {
        return epRuntime;
    }

    public Set<EsperStatement> getStatements() {
        return this.statements;
    }

    public synchronized void addStatement(EsperStatement statement) {
        statements.add(statement);
        if (this.initialised) {
            EPStatement epStatement = epServiceProvider.getEPAdministrator()
                    .createEPL(statement.getEPL());
            statement.setEPStatement(epStatement);
        }
    }

    public void sendEvent(Object event)
            throws InvalidEsperConfigurationException {
        LOG.debug("Sending event to Esper");
        if (epRuntime != null) {
            epRuntime.sendEvent(event);
        } else {
            LOG.error("Attempted to send message with null Esper Runtime.");
            throw new InvalidEsperConfigurationException(
                    "Esper Runtime is null. Have you initialized the template before you attempt to send an event?");
        }
        LOG.debug("Sent event to Esper");
    }

    public synchronized void initialize() throws InvalidEsperConfigurationException {
        if (this.initialised) {
            throw new InvalidEsperConfigurationException("EsperTemplate should only be initialised once");
        }
        this.initialised = true;
        LOG.debug("Initializing esper template");
        try {
            configureEPServiceProvider();
            epRuntime = epServiceProvider.getEPRuntime();
            if (this.unmatchedListener != null) {
                epRuntime.setUnmatchedListener(unmatchedListener);
            }
            setupEPStatements();
        } catch (Exception e) {
            LOG.error("An exception occured when attempting to initialize the esper template", e);
            throw new InvalidEsperConfigurationException(e.getMessage(), e);
        }
        LOG.debug("Finished initializing esper template");
    }

    public void cleanup() {
        epServiceProvider.destroy();
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
     *
     * @throws IOException
     * @throws EPException
     */
    private void configureEPServiceProvider() throws EPException, IOException {
        LOG.debug("Configuring the Esper Service Provider");
        if (this.configuration != null && this.configuration.exists()) {
            Configuration configuration = new Configuration();
            configuration = configuration.configure(this.configuration
                    .getFile());
            epServiceProvider = EPServiceProviderManager.getProvider(name,
                    configuration);
            LOG.info("Esper configured with a user-provided configuration", configuration);
        } else {
            epServiceProvider = EPServiceProviderManager.getProvider(name);
        }
        LOG.debug("Completed configuring the Esper Service Provider");
	}
}
