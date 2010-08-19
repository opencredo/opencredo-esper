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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.core.MessageChannel;
import org.springframework.integration.message.GenericMessage;
import org.springframework.util.Assert;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UnmatchedListener;
import com.espertech.esper.client.UpdateListener;

/**
 * Provides a simple inbound channel adapter that takes Esper events and places
 * them on the indicated Spring Integration channel.
 * 
 * @author Russ Miles (russell.miles@opencredo.com)
 * @author Jonas Partner (jonas.partner@opencredo.com)
 */
public class EventDrivenEsperInboundChannelAdapter implements UpdateListener, UnmatchedListener {
	private final static Logger LOG = LoggerFactory
			.getLogger(EventDrivenEsperInboundChannelAdapter.class);

	private final MessageChannel channel;

	private Long timeout;

    /**
     * Provide the channel required to send to 
     * @param channel
     */
    public EventDrivenEsperInboundChannelAdapter(MessageChannel channel) {
        this.channel = channel;
    }

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



	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.espertech.esper.client.UpdateListener#update(com.espertech.esper.
	 * client.EventBean[], com.espertech.esper.client.EventBean[])
	 */
	public void update(EventBean[] eventBeans, EventBean[] eventBeans1) {
		LOG.debug("Inbound channel adapter receiving an event from esper");
		
		Assert.notNull(channel);

		GenericMessage<EventBean[]> message = new GenericMessage<EventBean[]>(
				eventBeans);

		if (timeout != null) {
			LOG.debug("Sending message (" + message + ") to channel " + channel + " with timeout " + timeout);
			channel.send(message, timeout);
		} else {
			LOG.debug("Sending message (" + message + ") to channel " + channel);
			channel.send(message);
		}
		
		LOG.debug("Inbound channel adapter received an event from esper");
	}

	/* (non-Javadoc)
	 * @see com.espertech.esper.client.UnmatchedListener#update(com.espertech.esper.client.EventBean)
	 */
	public void update(EventBean eventBean) {
		LOG.debug("Inbound channel adapter receiving an unmatched listener event from esper");
		
		Assert.notNull(channel);

		GenericMessage<EventBean> message = new GenericMessage<EventBean>(
				eventBean);

		if (timeout != null) {
			LOG.debug("Sending message (" + message + ") to channel " + channel + " with timeout " + timeout);
			channel.send(message, timeout);
		} else {
			LOG.debug("Sending message (" + message + ") to channel " + channel);
			channel.send(message);
		}
		
		LOG.debug("Inbound channel adapter received an unmatched listener event from esper");
	}
}
