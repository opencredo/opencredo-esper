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

package org.opencredo.esper.integration.throughput;

import org.opencredo.esper.EsperStatement;
import org.opencredo.esper.EsperTemplate;
import org.opencredo.esper.integration.interceptor.EsperWireTap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.integration.channel.AbstractMessageChannel;
import org.springframework.integration.channel.ChannelInterceptor;

/**
 * Provides a spring integration {@link ChannelInterceptor} default implementation
 * of a channel throughput monitor.
 * 
 * @author Russ Miles (russ.miles@opencredo.com)
 * 
 */
public class EsperChannelThroughputMonitor implements InitializingBean,
		DisposableBean {
	private final static Logger LOG = LoggerFactory
			.getLogger(EsperChannelThroughputMonitor.class);

	private AbstractMessageChannel channel;
	private String timeSample = "1 second";
	private long throughput;

	private EsperTemplate template;

	public EsperChannelThroughputMonitor(AbstractMessageChannel channel) {
		super();
		this.channel = channel;
		
	}
	
	public void setTimeSample(String timeSample) {
		this.timeSample = timeSample;
	}

	public long getThroughput() {
		return throughput;
	}

	public void afterPropertiesSet() throws Exception {

		String throughputQuery = "select count(*) as throughput from org.opencredo.esper.integration.MessageContext.win:time_batch(" + this.timeSample + ")";
		
		this.template = new EsperTemplate();

		EsperStatement statement = new EsperStatement(throughputQuery);

		statement.setSubscriber(this);

		template.addStatement(statement);

		template.initialize();

		EsperWireTap wireTap = new EsperWireTap(template);
		
		// We only want to listen to one event per message sent
		wireTap.setPostSend(true);
		wireTap.setPostSend(false);
		
		wireTap.setSendContext(true);

		this.channel.addInterceptor(wireTap);

	}

	/**
	 * Method used by the Esper subscriber contract where the payload of the
	 * event is passed to the subscriber. In this case the payload is a
	 * calculated throughput.
	 * 
	 * @param throughput
	 *            the calculated throughput for the channel
	 */
	public void update(long throughput) {
		LOG.debug("Received throughput of " + throughput);
		this.throughput = throughput;
	}

	public void destroy() throws Exception {
		this.template.cleanup();
	}
}
