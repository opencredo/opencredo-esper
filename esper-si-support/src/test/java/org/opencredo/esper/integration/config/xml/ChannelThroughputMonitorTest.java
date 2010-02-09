package org.opencredo.esper.integration.config.xml;

import static org.junit.Assert.*;
import junit.framework.Assert;

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
	EsperChannelThroughputMonitor throughputMonitor;
	
	@Autowired
	@Qualifier("messageChannel")
	MessageChannel channel;
	
	@Test
	public void testMessageThroughputPerSampleWindow() {
		
		for (int i = 0; i < 10; i++) {
			channel.send(new GenericMessage<SampleEvent>(new SampleEvent()));
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		long throughput = throughputMonitor.getThroughput();
		
		System.out.println("Throughput is: " + throughputMonitor.getThroughput());
		
		assertTrue(throughput > 0);
	}

}
