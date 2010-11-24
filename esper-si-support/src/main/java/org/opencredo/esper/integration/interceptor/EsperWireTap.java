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

package org.opencredo.esper.integration.interceptor;

import org.opencredo.esper.EsperTemplate;
import org.opencredo.esper.integration.IntegrationOperation;
import org.opencredo.esper.integration.MessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.channel.ChannelInterceptor;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;

/**
 * Provides a spring integration {@link ChannelInterceptor} implementation that
 * takes messages from the 4 interception points (pre-send, post-receive,
 * post-send, pre-receive) and sends either the message context or the message
 * payload to esper.
 * 
 * @author Russ Miles (russ.miles@opencredo.com)
 * @author Jonas Partner (jonas.partner@opencredo.com)
 */
public class EsperWireTap implements ChannelInterceptor {
    private static final String SENT_MESSAGE_PAYLOAD_TO_ESPER_FROM_SOURCE = "Sent message payload to esper from source '";

	private static final String SENT_MESSAGE_CONTEXT_TO_ESPER_FROM_SOURCE = "Sent message context to esper from source '";

	private final static Logger LOG = LoggerFactory.getLogger(EsperWireTap.class);

    private final EsperTemplate template;

    private final String sourceId;

    private volatile boolean sendContext = false;

    private volatile boolean preSend = true;

    private volatile boolean postSend = true;

    private volatile boolean preReceive = true;

    private volatile boolean postReceive = true;

    public EsperWireTap(EsperTemplate template, String sourceId) {
        this.template = template;
        this.sourceId = sourceId;
    }

    public void setSendContext(boolean sendContext) {
        this.sendContext = sendContext;
    }

    public void setPreSend(boolean preSend) {
        this.preSend = preSend;
    }

    public void setPostSend(boolean postSend) {
        this.postSend = postSend;
    }

    public void setPreReceive(boolean preReceive) {
        this.preReceive = preReceive;
    }

    public void setPostReceive(boolean postReceive) {
        this.postReceive = postReceive;
    }

    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        if (this.preSend) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Sending a pre-send message to esper from source '" + sourceId + "'");
            }

            if (sendContext) {
                MessageContext context = new MessageContext(message, channel, IntegrationOperation.PRE_SEND, sourceId);
                template.sendEvent(context);
                if (LOG.isDebugEnabled()) {
                    LOG.debug(SENT_MESSAGE_CONTEXT_TO_ESPER_FROM_SOURCE + sourceId + "'");
                }
            } else {
                template.sendEvent(message.getPayload());
                if (LOG.isDebugEnabled()) {
                    LOG.debug(SENT_MESSAGE_PAYLOAD_TO_ESPER_FROM_SOURCE + sourceId + "'");
                }
            }
        }

        return message;
    }

    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        if (message == null) {
            // If no message received - no event is created
            return message;
        }

        if (this.postReceive) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Sending a post-receive message to esper from source '" + sourceId + "'");
            }

            if (sendContext) {
                MessageContext context;

                // Note that sometimes message might not exist e.g. poller polls
                // empty queue.
                context = new MessageContext(message, channel, IntegrationOperation.POST_RECEIVE, sourceId);

                template.sendEvent(context);
                if (LOG.isDebugEnabled()) {
                    LOG.debug(SENT_MESSAGE_CONTEXT_TO_ESPER_FROM_SOURCE + sourceId + "'");
                }
            } else {
                template.sendEvent(message.getPayload());
                if (LOG.isDebugEnabled()) {
                    LOG.debug(SENT_MESSAGE_PAYLOAD_TO_ESPER_FROM_SOURCE + sourceId + "'");
                }
            }
        }

        return message;
    }

    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        if (this.postSend) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Sending a post-send message to esper from source '" + sourceId + "'");
            }

            if (sendContext) {
                MessageContext context = new MessageContext(message, channel, sent, sourceId);
                template.sendEvent(context);
                if (LOG.isDebugEnabled()) {
                    LOG.debug(SENT_MESSAGE_CONTEXT_TO_ESPER_FROM_SOURCE + sourceId + "'");
                }
            } else {
                template.sendEvent(message.getPayload());
                if (LOG.isDebugEnabled()) {
                    LOG.debug(SENT_MESSAGE_PAYLOAD_TO_ESPER_FROM_SOURCE + sourceId + "'");
                }
            }
        }
    }

    public boolean preReceive(MessageChannel channel) {
        if (this.preReceive) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Sending a pre-receive message to esper from source '" + sourceId + "'");
            }

            MessageContext context = new MessageContext(channel, sourceId);
            template.sendEvent(context);
            if (LOG.isDebugEnabled()) {
                LOG.debug(SENT_MESSAGE_CONTEXT_TO_ESPER_FROM_SOURCE + sourceId + "'");
            }
        }
        return true;
    }
}
