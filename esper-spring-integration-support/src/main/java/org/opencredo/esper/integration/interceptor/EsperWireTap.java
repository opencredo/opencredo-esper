package org.opencredo.esper.integration.interceptor;

import org.springframework.integration.channel.ChannelInterceptor;
import org.springframework.integration.core.Message;
import org.springframework.integration.core.MessageChannel;

import org.opencredo.esper.EsperTemplate;
import org.opencredo.esper.integration.MessageContext;
import org.opencredo.esper.integration.IntegrationOperation;

public class EsperWireTap implements ChannelInterceptor  {
	
	private EsperTemplate template;
	
	private boolean sendContext;
	
	public void setTemplate(EsperTemplate template) {
		this.template = template;
	}

	public void setSendContext(boolean sendContext) {
		this.sendContext = sendContext;
	}

	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		if (sendContext) {
			MessageContext context = new MessageContext(message, channel, IntegrationOperation.PRE_SEND);
			template.sendEvent(context);
		} else {
			template.sendEvent(message.getPayload());
		}
		
		return message;
	}
	
	public Message<?> postReceive(Message<?> message, MessageChannel channel) {
		if (sendContext) {
			MessageContext context = new MessageContext(message, channel, IntegrationOperation.POST_RECEIVE);
			template.sendEvent(context);
		} else {
			template.sendEvent(message.getPayload());
		}
		
		return message;
	}

	public void postSend(Message<?> message, MessageChannel channel,
			boolean sent) {
		if (sendContext) {
			MessageContext context = new MessageContext(message, channel, sent);
			template.sendEvent(context);
		} else {
			template.sendEvent(message.getPayload());
		}
	}

	public boolean preReceive(MessageChannel channel) {
		MessageContext context = new MessageContext(channel);
		template.sendEvent(context);
		
		return true;
	}
}
