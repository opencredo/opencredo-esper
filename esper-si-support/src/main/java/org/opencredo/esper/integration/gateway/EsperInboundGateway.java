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

package org.opencredo.esper.integration.gateway;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.integration.core.MessageChannel;
import org.springframework.integration.message.GenericMessage;
import org.springframework.util.Assert;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

/**
 * Provides a simple inbound channel adapter that takes Esper events and places
 * them on the indicated Spring Integration channel.
 * 
 * @author Russ Miles (russell.miles@opencredo.com)
 * 
 */
public class EsperInboundGateway implements UpdateListener {

	private MessageChannel channel;

	private Long timeout;

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
	 * Sets the channel that incoming Esper events are to be send to.
	 * 
	 * @param channel
	 *            The channel that will receive messages whose payload contains
	 *            esper events
	 */
	@Required
	public void setChannel(MessageChannel channel) {
		this.channel = channel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.espertech.esper.client.UpdateListener#update(com.espertech.esper.
	 * client.EventBean[], com.espertech.esper.client.EventBean[])
	 */
	public void update(EventBean[] eventBeans, EventBean[] eventBeans1) {

		Assert.notNull(channel);

		GenericMessage<EventBean[]> message = new GenericMessage<EventBean[]>(
				eventBeans);

		if (timeout != null) {
			channel.send(message, timeout);
		} else {
			channel.send(message);
		}
	}
}
