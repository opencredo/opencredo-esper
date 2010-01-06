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

package org.opencredo.esper.integration;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opencredo.esper.sample.SampleEvent;
import org.opencredo.esper.sample.SampleService;
import org.opencredo.esper.sample.Status;
import org.opencredo.esper.sample.ThroughputMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class WireTapToGatewayThroughTransformerTest {

	@Autowired
	private SampleService sampleService;
	
	@Autowired
	private ThroughputMonitor monitor;
	
	private static final int NUMBER_OF_EVENTS = 1000;

	@Test
	public void sendADomainMessageAndAssertThatListenerIsInvoked() {
		// a number of events to process
		SampleEvent[] events = new SampleEvent[NUMBER_OF_EVENTS];
		
		// Create and send events
		for (int x = 0; x < NUMBER_OF_EVENTS; x++) {
			events[x] = new SampleEvent();
			this.sampleService.processEvent(events[x]);
		}
		
		// Take a look at all the events and see if they're processed
		for (int x = 0; x < NUMBER_OF_EVENTS; x++) {
			SampleEvent event = events[x];
			assertSame("Comparing event[" + x + "] for completion", Status.COMPLETE, event.getStatus());
		}
	
		assertTrue(monitor.getAverageThroughput() > 0);
	}
}
