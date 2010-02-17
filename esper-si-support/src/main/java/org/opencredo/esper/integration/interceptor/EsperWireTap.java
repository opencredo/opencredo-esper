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

package org.opencredo.esper.integration.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.channel.ChannelInterceptor;
import org.springframework.integration.core.Message;
import org.springframework.integration.core.MessageChannel;

import org.opencredo.esper.EsperTemplate;
import org.opencredo.esper.integration.MessageContext;
import org.opencredo.esper.integration.IntegrationOperation;

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
    private final static Logger LOG = LoggerFactory
            .getLogger(EsperWireTap.class);

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
            LOG.debug("Sending a pre-send message to esper");
            if (sendContext) {
                MessageContext context = new MessageContext(message, channel,
                        IntegrationOperation.PRE_SEND, sourceId);
                template.sendEvent(context);
                LOG.debug("Sent message context to esper");
            } else {
                template.sendEvent(message.getPayload());
                LOG.debug("Sent message payload to esper");
            }
            LOG.debug("Finished sending a pre-send message to esper");
        }

        return message;
    }

    public Message<?> postReceive(Message<?> message, MessageChannel channel) {

        if (this.postReceive) {
            LOG.debug("Sending a post-receive message to esper");
            if (sendContext) {
                MessageContext context = new MessageContext(message, channel,
                        IntegrationOperation.POST_RECEIVE, sourceId);
                template.sendEvent(context);
                LOG.debug("Sent message context to esper");
            } else {
                template.sendEvent(message.getPayload());
                LOG.debug("Sent message payload to esper");
            }
            LOG.debug("Finished sending a post-receive message to esper");
        }

        return message;
    }

    public void postSend(Message<?> message, MessageChannel channel,
                         boolean sent) {

        if (this.postSend) {
            LOG.debug("Sending a post-send message to esper");

            if (sendContext) {
                MessageContext context = new MessageContext(message, channel,
                        sent, sourceId);
                template.sendEvent(context);
                LOG.debug("Sent message context to esper");
            } else {
                template.sendEvent(message.getPayload());
                LOG.debug("Sent message payload to esper");
            }
            LOG.debug("Finished sending a post-send message to esper");
        }
    }

    public boolean preReceive(MessageChannel channel) {
        if (this.preReceive) {
            LOG.debug("Sending a pre-receive message to esper");
            MessageContext context = new MessageContext(channel, sourceId);
            template.sendEvent(context);
            LOG.debug("Sent message context to esper");
            LOG.debug("Finished sending a pre-receive message to esper");
        }
        return true;
    }
}
