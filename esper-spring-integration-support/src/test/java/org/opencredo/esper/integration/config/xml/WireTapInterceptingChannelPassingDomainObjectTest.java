package org.opencredo.esper.integration.config.xml;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opencredo.esper.sample.CallRecordingListener;
import org.opencredo.esper.sample.SampleEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.core.MessageChannel;
import org.springframework.integration.message.GenericMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class WireTapInterceptingChannelPassingDomainObjectTest {

	@Autowired
	@Qualifier("domainEvents")
	MessageChannel channel;
	
	@Autowired
	CallRecordingListener listener;
	
	@Test
	public void sendADomainMessageAcrossChannelAndAssertThatListenerIsInvoked() {
		
		channel.send(new GenericMessage<SampleEvent>(new SampleEvent()), 500l);

		assertEquals(2, listener.getNumberOfTimesInvoked());
	}
}
