package org.opencredo.esper.integration;

import org.springframework.integration.core.Message;
import org.springframework.integration.core.MessageChannel;

public class MessageContext {
	private final Message<?> message;
	private final MessageChannel channel;
	private final IntegrationOperation operation;
	private final boolean sent;
	
	public MessageContext(Message<?> message, MessageChannel channel, IntegrationOperation operation) {
		super();
		this.message = message;
		this.channel = channel;
		this.operation = operation;
		this.sent = false;
	}
	
	public MessageContext(Message<?> message, MessageChannel channel, boolean sent) {
		super();
		this.message = message;
		this.channel = channel;
		this.operation = IntegrationOperation.POST_SEND;
		this.sent = sent;
	}
	
	public MessageContext(MessageChannel channel) {
		super();
		this.message = null;
		this.channel = channel;
		this.operation = IntegrationOperation.PRE_RECEIVE;
		this.sent = false;
	}

	public Message<?> getMessage() {
		return message;
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
}
