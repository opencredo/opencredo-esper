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

package org.opencredo.esper.integration.config.xml;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opencredo.esper.EsperTemplate;
import org.opencredo.esper.integration.MessageContext;
import org.opencredo.esper.sample.CallRecordingListener;
import org.opencredo.esper.sample.CallRecordingUnmatchedListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class UnmatchedListenerInboundChannelAdapterParserTest {

	@Autowired
	EsperTemplate template;
	
	@Autowired
	CallRecordingListener listener;
	
	@Autowired
	CallRecordingUnmatchedListener unmatchedListener;

	@Test
	public void sendAnEsperContextMessageAndAssertThatListenerIsInvoked() {
		template.sendEvent(new MessageContext(new DirectChannel()));
		
		template.sendEvent("A simple string event object!");
		
		assertEquals(1, listener.getNumberOfTimesInvoked());
		
		assertEquals(1, unmatchedListener.getNumberOfTimesInvoked());
	}
}
