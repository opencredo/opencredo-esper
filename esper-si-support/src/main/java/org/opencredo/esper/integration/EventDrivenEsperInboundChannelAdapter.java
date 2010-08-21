/*
 * OpenCredo-Esper - simplifies adopting Esper in Java applications. 
 * Copyright (C) 2010  OpenCredo Ltd.
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.opencredo.esper.integration;

import org.opencredo.esper.EsperStatement;
import org.opencredo.esper.EsperTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.integration.core.MessageChannel;
import org.springframework.integration.message.GenericMessage;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UnmatchedListener;
import com.espertech.esper.client.UpdateListener;

/**
 * Provides a simple inbound channel adapter that takes Esper events and places
 * them on the indicated Spring Integration channel. <br>
 * By default this adapter creates an Esper template with no name, by specifying
 * {@link #setTemplateName(String)} you will be able to create Esper template
 * with name.
 * 
 * @author Russ Miles (russell.miles@opencredo.com)
 * @author Jonas Partner (jonas.partner@opencredo.com)
 * @author Tomas Lukosius (tomas.lukosius@opencredo.com)
 */
public class EventDrivenEsperInboundChannelAdapter implements InitializingBean, DisposableBean, UpdateListener,
        UnmatchedListener {
    private final static Logger LOG = LoggerFactory.getLogger(EventDrivenEsperInboundChannelAdapter.class);

    private final MessageChannel channel;
    private final String eplQuery;
    private Long timeout;
    private String templateName;

    private EsperTemplate template;

    /**
     * Provide the channel required to send to
     * 
     * @param channel
     * @param epl
     */
    public EventDrivenEsperInboundChannelAdapter(MessageChannel channel, String epl) {
        this.channel = channel;
        this.eplQuery = epl;
    }

    /**
     * Provides a timeout that is used when sending messages to the indicated
     * channel.
     * 
     * @param timeout
     *            The timeout to be used when sending messages to the specified
     *            channel.
     */
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    /**
     * @param templateName
     *            the templateName to set
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     * @throws Exception
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {
        this.template = new EsperTemplate();
        this.template.setName(templateName);

        if (StringUtils.hasText(eplQuery)) {
            EsperStatement statement = new EsperStatement(eplQuery);
            statement.addListener(this);
            template.addStatement(statement);
        } else {
            template.setUnmatchedListener(this);
        }

        template.initialize();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.espertech.esper.client.UpdateListener#update(com.espertech.esper.
     * client.EventBean[], com.espertech.esper.client.EventBean[])
     */
    public void update(EventBean[] eventBeans, EventBean[] eventBeans1) {
        LOG.debug("Inbound channel adapter receiving an event from esper");

        Assert.notNull(channel);

        GenericMessage<EventBean[]> message = new GenericMessage<EventBean[]>(eventBeans);

        if (timeout != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Sending message (" + message + ") to channel " + channel + " with timeout " + timeout);
            }
            channel.send(message, timeout);
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Sending message (" + message + ") to channel " + channel);
            }
            channel.send(message);
        }

        LOG.debug("Inbound channel adapter received an event from esper");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.espertech.esper.client.UnmatchedListener#update(com.espertech.esper
     * .client.EventBean)
     */
    public void update(EventBean eventBean) {
        LOG.debug("Inbound channel adapter receiving an unmatched listener event from esper");

        Assert.notNull(channel);

        GenericMessage<EventBean> message = new GenericMessage<EventBean>(eventBean);

        if (timeout != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Sending message (" + message + ") to channel " + channel + " with timeout " + timeout);
            }
            channel.send(message, timeout);
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Sending message (" + message + ") to channel " + channel);
            }
            channel.send(message);
        }

        LOG.debug("Inbound channel adapter received an unmatched listener event from esper");
    }

    /**
     * @throws Exception
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    public void destroy() throws Exception {
        this.template.cleanup();
    }
}
