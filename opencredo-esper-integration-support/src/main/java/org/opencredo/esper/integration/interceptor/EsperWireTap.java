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

import org.springframework.integration.channel.ChannelInterceptor;
import org.springframework.integration.core.Message;
import org.springframework.integration.core.MessageChannel;

import org.opencredo.esper.EsperTemplate;
import org.opencredo.esper.integration.MessageContext;
import org.opencredo.esper.integration.IntegrationOperation;

public class EsperWireTap implements ChannelInterceptor  {
	
	private EsperTemplate template;
	
	private boolean sendContext = false;
	
	private boolean preSend = true;
	
	private boolean postSend = true;
	
	private boolean preReceive = true;
	
	private boolean postReceive = true;
	
	public EsperWireTap(EsperTemplate template) {
		this.template = template;
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
			if (sendContext) {
				MessageContext context = new MessageContext(message, channel, IntegrationOperation.PRE_SEND);
				template.sendEvent(context);
			} else {
				template.sendEvent(message.getPayload());
			}
		}
		
		return message;
	}
	
	public Message<?> postReceive(Message<?> message, MessageChannel channel) {
		if (this.postReceive) {
			if (sendContext) {
				MessageContext context = new MessageContext(message, channel, IntegrationOperation.POST_RECEIVE);
				template.sendEvent(context);
			} else {
				template.sendEvent(message.getPayload());
			}
		}
		
		return message;
	}

	public void postSend(Message<?> message, MessageChannel channel,
			boolean sent) {
		if (this.postSend) {
			if (sendContext) {
				MessageContext context = new MessageContext(message, channel, sent);
				template.sendEvent(context);
			} else {
				template.sendEvent(message.getPayload());
			}
		}
	}

	public boolean preReceive(MessageChannel channel) {
		if (this.preReceive) {
			MessageContext context = new MessageContext(channel);
			template.sendEvent(context);
		}
		
		return true;
	}
}
