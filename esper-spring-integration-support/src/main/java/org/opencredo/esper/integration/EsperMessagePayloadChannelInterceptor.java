package org.opencredo.esper.integration;

import org.springframework.integration.channel.interceptor.ChannelInterceptorAdapter;
import org.springframework.integration.core.Message;
import org.springframework.integration.core.MessageChannel;

import org.opencredo.esper.EsperEngine;

public class EsperMessagePayloadChannelInterceptor extends ChannelInterceptorAdapter  {
	
	private EsperEngine esperBean;
	
	public void setEsperBean(EsperEngine esperBean) {
		this.esperBean = esperBean;
	}
	
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		esperBean.sendEvent(message.getPayload());
		return message;
	}
}
