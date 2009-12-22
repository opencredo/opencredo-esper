package org.opencredo.esper.integration.config.xml;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opencredo.esper.integration.interceptor.EsperWireTap;
import org.opencredo.esper.sample.CallRecordingListener;
import org.opencredo.esper.sample.SampleEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.message.GenericMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class WireTapParserTest {

	@Autowired
	EsperWireTap wireTap;
	
	@Autowired
	CallRecordingListener listener;
	
	@Test
	public void sendAStringMessageAndAssertThatListenerIsInvoked() {
		
		this.wireTap.preSend(new GenericMessage<SampleEvent>(new SampleEvent()), null);
		
		assertEquals(1, listener.getNumberOfTimesInvoked());
	}
}
