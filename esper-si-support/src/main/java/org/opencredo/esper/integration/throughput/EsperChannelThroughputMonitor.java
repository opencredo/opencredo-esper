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

package org.opencredo.esper.integration.throughput;

import org.opencredo.esper.EsperStatement;
import org.opencredo.esper.EsperTemplate;
import org.opencredo.esper.integration.IntegrationOperation;
import org.opencredo.esper.integration.MessageContext;
import org.opencredo.esper.integration.interceptor.EsperWireTap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.integration.channel.AbstractMessageChannel;
import org.springframework.integration.channel.ChannelInterceptor;
import org.springframework.integration.channel.PollableChannel;

/**
 * Provides a spring integration {@link ChannelInterceptor} default
 * implementation of a channel throughput monitor.
 * 
 * @author Russ Miles (russ.miles@opencredo.com)
 * @author Jonas Partner (jonas.partner@opencredo.com)
 * 
 */
public class EsperChannelThroughputMonitor implements InitializingBean, DisposableBean {

    private static final String THROUGHPUT_SUFFIX = "throughput";

    private final static Logger LOG = LoggerFactory.getLogger(EsperChannelThroughputMonitor.class);

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
        String windowName = sourceId + THROUGHPUT_SUFFIX;

        StringBuilder createSourceWindow = new StringBuilder();
        createSourceWindow.append("insert into ").append(windowName);
        createSourceWindow.append(" select sourceId, operation from ").append(MessageContext.class.getName());
        createSourceWindow.append(" where sourceId = '").append(this.sourceId).append("'");

        this.template = new EsperTemplate();
        template.addStatement(new EsperStatement(createSourceWindow.toString()));

        EsperWireTap wireTap = new EsperWireTap(template, this.sourceId);
        wireTap.setSendContext(true);
        // We always want to listen to event per message sent
        wireTap.setPostSend(true);
        wireTap.setPreSend(false);
        wireTap.setPreReceive(false);
        wireTap.setPostReceive(false);

        if (channel instanceof PollableChannel) {
            // This channel is pollable - need to calculate messages received
            // and average queue size.

            // We want to know how many messages were received.
            wireTap.setPostReceive(true);

            StringBuilder sb = new StringBuilder();
            sb.append("select count(PS), count(PR) from pattern [every PS=");
            sb.append(windowName).append("(operation=").append(IntegrationOperation.class.getName()).append(".")
                    .append(IntegrationOperation.POST_SEND).append(") OR every PR=");
            sb.append(windowName).append("(operation=").append(IntegrationOperation.class.getName()).append(".")
                    .append(IntegrationOperation.POST_RECEIVE).append(")]");
            sb.append(".win:time_batch(").append(timeSample).append(")");
            EsperStatement listenForSourceId = new EsperStatement(sb.toString());
            listenForSourceId.setSubscriber(this);
            template.addStatement(listenForSourceId);

        } else {

            StringBuilder sb = new StringBuilder();
            sb.append("select count(*) as throughput from ").append(windowName);
            sb.append(".win:time_batch(").append(this.timeSample).append(")");
            sb.append(" where operation=").append(IntegrationOperation.class.getName()).append(".")
                    .append(IntegrationOperation.POST_SEND);

            EsperStatement listenForSourceId = new EsperStatement(sb.toString());
            listenForSourceId.setSubscriber(this);
            template.addStatement(listenForSourceId);
        }
        template.initialize();

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
        if (LOG.isDebugEnabled()) {
            LOG.debug("Received throughput of " + throughput + " on channel - " + this.channel.getName());
        }
        this.throughput = throughput;
    }

    public void update(Long ps_count, Long pr_count) {

        ps_count = ps_count == null ? 0 : ps_count;
        pr_count = pr_count == null ? 0 : pr_count;

        if (LOG.isDebugEnabled()) {
            LOG.debug("Sent throughput of " + ps_count + ", received throughput of " + pr_count + " on pollable channel - "
                    + this.channel.getName());
        }
        this.throughput = pr_count;
    }

    public void destroy() throws Exception {
        this.template.cleanup();
    }
}
