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

package org.opencredo.esper.integration;

import org.springframework.integration.core.Message;
import org.springframework.integration.core.MessageChannel;
import org.springframework.integration.core.MessageHeaders;

import java.util.UUID;

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

	public MessageContext(Message<?> message, MessageChannel channel,
                          IntegrationOperation operation, String sourceId) {
		super();
		this.message = message;
		this.channel = channel;
		this.operation = operation;
        this.sourceId = sourceId;
        this.sent = false;
        this.messageId =  message.getHeaders().get(MessageHeaders.ID).toString();
	}

	public MessageContext(Message<?> message, MessageChannel channel,
                          boolean sent, String sourceId) {
		super();
		this.message = message;
		this.channel = channel;
        this.sourceId = sourceId;
        this.operation = IntegrationOperation.POST_SEND;
		this.sent = sent;
         this.messageId =  message.getHeaders().get(MessageHeaders.ID).toString();
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

    public String getMessageId(){
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
