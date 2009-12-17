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
