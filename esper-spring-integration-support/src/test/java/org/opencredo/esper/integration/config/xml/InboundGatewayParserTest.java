package org.opencredo.esper.integration.config.xml;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opencredo.esper.EsperTemplate;
import org.opencredo.esper.integration.MessageContext;
import org.opencredo.esper.sample.CallRecordingListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class InboundGatewayParserTest {

	@Autowired
	EsperTemplate template;
	
	@Autowired
	CallRecordingListener listener;

	@Test
	public void sendAnEsperContextMessageAndAssertThatListenerIsInvoked() {
		template.sendEvent(new MessageContext(new DirectChannel()));
		
		assertEquals(1, listener.getNumberOfTimesInvoked());
	}
}
