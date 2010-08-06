/*
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
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
 * @author Jonas Partner (jonas.partner@opencredo.com)
 * 
 */
public class EsperChannelThroughputMonitor implements InitializingBean,
		DisposableBean {

    private static final String THROUGHPUT_SUFFIX = "throughput";

    private final static Logger LOG = LoggerFactory
			.getLogger(EsperChannelThroughputMonitor.class);

	private final AbstractMessageChannel channel;
	private final String sourceId;


    private String timeSample = "1 second";
	private long throughput;

	private EsperTemplate template;

	public EsperChannelThroughputMonitor(AbstractMessageChannel channel, String sourceId) {
		super();
		this.channel = channel;
        this.sourceId = sourceId;
		
	}
	
	public void setTimeSample(String timeSample) {
		this.timeSample = timeSample;
	}

	public long getThroughput() {
		return throughput;
	}

	public void afterPropertiesSet() throws Exception {
        String windowName =  sourceId + THROUGHPUT_SUFFIX ;

		String createSourceWindow = "insert into " + windowName + " select * from org.opencredo.esper.integration.MessageContext where sourceId = '"
                + this.sourceId + "'";
		
		this.template = new EsperTemplate();
        template.addStatement(new EsperStatement(createSourceWindow));

        EsperStatement listenForSourceId = new EsperStatement("select count(*) as throughput from " + windowName + ".win:time_batch(" + this.timeSample + ")");
        listenForSourceId.setSubscriber(this);
        template.addStatement(listenForSourceId);

		template.initialize();

		EsperWireTap wireTap = new EsperWireTap(template, this.sourceId);
		
		// We only want to listen to one event per message sent
		wireTap.setPostSend(true);
		wireTap.setPreSend(false);
        wireTap.setPreReceive(false);
        wireTap.setPostReceive(false);
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
		LOG.debug("Received throughput of " + throughput + " on channel " + this.channel.getName());
		this.throughput = throughput;
	}

	public void destroy() throws Exception {
		this.template.cleanup();
	}
}
