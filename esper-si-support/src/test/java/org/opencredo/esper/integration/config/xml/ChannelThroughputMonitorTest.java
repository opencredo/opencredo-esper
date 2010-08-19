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

package org.opencredo.esper.integration.config.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opencredo.esper.integration.throughput.EsperChannelThroughputMonitor;
import org.opencredo.esper.sample.SampleEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.core.MessageChannel;
import org.springframework.integration.message.GenericMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ChannelThroughputMonitorTest {

    @Autowired
    @Qualifier("throughputMonitor")
    EsperChannelThroughputMonitor throughputMonitor;

    @Autowired
    @Qualifier("throughputMonitorTwo")
    EsperChannelThroughputMonitor throughputMonitorTwo;

    @Autowired
    @Qualifier("messageChannel")
    MessageChannel channel;

    @Autowired
    @Qualifier("messageChannelTwo")
    MessageChannel channelTwo;

    @Test
    public void testMessageThroughputPerSampleWindow() throws Exception {

        for (int i = 0; i < 10; i++) {
            channel.send(new GenericMessage<SampleEvent>(new SampleEvent()));
        }

        Thread.sleep(1100);
        long throughput = throughputMonitor.getThroughput();

        System.out.println("Throughput is: " + throughputMonitor.getThroughput());

        assertEquals("Throughput calculated incorrectly ", 10, throughput);
    }

    @Test
    public void testMessageThroughputOnMutlipleChannels() throws Exception {

        for (int i = 0; i < 10; i++) {
            channel.send(new GenericMessage<SampleEvent>(new SampleEvent()));
        }

        for (int i = 0; i < 20; i++) {
            channelTwo.send(new GenericMessage<SampleEvent>(new SampleEvent()));
        }

        Thread.sleep(1100);

        long throughput = throughputMonitor.getThroughput();

        System.out.println("Throughput is: " + throughputMonitor.getThroughput());

        assertEquals("Throughput one calculated incorrectly ", 10, throughput);

        long throughputTwo = throughputMonitorTwo.getThroughput();

        System.out.println("Throughput two is: " + throughputMonitorTwo.getThroughput());

        assertEquals("Throughput two calculated incorrectly ", 20, throughputTwo);
    }

}
