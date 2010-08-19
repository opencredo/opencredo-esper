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

import org.springframework.integration.core.Message;
import org.springframework.integration.core.MessageChannel;
import org.springframework.integration.core.MessageHeaders;

/**
 * Provides the complete message context as it can be retrieved from Spring
 * Integration.
 * 
 * @author Russ Miles (russ.miles@opencredo.com)
 * 
 */
public class MessageContext {
    private final Message<?> message;
    private final MessageChannel channel;
    private final IntegrationOperation operation;
    private final boolean sent;
    private final String messageId;
    private final String sourceId;

    public MessageContext(Message<?> message, MessageChannel channel, IntegrationOperation operation, String sourceId) {
        super();
        this.message = message;
        this.channel = channel;
        this.operation = operation;
        this.sourceId = sourceId;
        this.sent = false;
        this.messageId = message.getHeaders().get(MessageHeaders.ID).toString();
    }

    public MessageContext(Message<?> message, MessageChannel channel, boolean sent, String sourceId) {
        super();
        this.message = message;
        this.channel = channel;
        this.sourceId = sourceId;
        this.operation = IntegrationOperation.POST_SEND;
        this.sent = sent;
        this.messageId = message.getHeaders().get(MessageHeaders.ID).toString();
    }

    public MessageContext(MessageChannel channel, String sourceId) {
        super();
        this.sourceId = sourceId;
        this.message = null;
        this.channel = channel;
        this.operation = IntegrationOperation.PRE_RECEIVE;
        this.sent = false;
        this.messageId = null;
    }

    public Message<?> getMessage() {
        return message;
    }

    public String getMessageId() {
        return this.messageId;
    }

    public MessageChannel getChannel() {
        return channel;
    }

    public IntegrationOperation getOperation() {
        return operation;
    }

    public boolean isSent() {
        return sent;
    }

    public String getSourceId() {
        return sourceId;
    }
}
