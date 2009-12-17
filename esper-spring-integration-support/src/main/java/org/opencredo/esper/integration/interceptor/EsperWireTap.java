package org.opencredo.esper.integration.interceptor;

import org.springframework.integration.channel.interceptor.ChannelInterceptorAdapter;
import org.springframework.integration.core.Message;
import org.springframework.integration.core.MessageChannel;

import org.opencredo.esper.EsperTemplate;

public class EsperWireTap extends ChannelInterceptorAdapter  {
	
	private EsperTemplate template;
	
	public void setEsperBean(EsperTemplate esperBean) {
		this.template = esperBean;
	}
	
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		template.sendEvent(message.getPayload());
		return message;
	}
}
