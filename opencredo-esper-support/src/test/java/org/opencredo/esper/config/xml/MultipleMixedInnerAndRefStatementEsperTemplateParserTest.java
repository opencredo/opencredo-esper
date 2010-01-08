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

package org.opencredo.esper.config.xml;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opencredo.esper.EsperStatement;
import org.opencredo.esper.EsperTemplate;
import org.opencredo.esper.sample.CallRecordingListener;
import org.opencredo.esper.sample.SampleEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.espertech.esper.client.UpdateListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class MultipleMixedInnerAndRefStatementEsperTemplateParserTest {

	@Autowired
	EsperTemplate template;
	
	@Test
	public void testTemplateInitializesWithOneStatment() {
		
		Set<EsperStatement> statements = template.getStatements();
		
		assertEquals(2, statements.size());
	}
	
	@Test
	public void testSendSampleEvent() {
		
		Set<EsperStatement> statements = template.getStatements();
		
		assertEquals(2, statements.size());
		
		EsperStatement[] statementsArray = new EsperStatement[statements.size()];
			
		statements.toArray(statementsArray);
		
		Set<UpdateListener> listeners = statementsArray[0].getListeners();
		
		assertEquals(1, listeners.size());
		
		UpdateListener[] listenersArray = new UpdateListener[listeners.size()];
			
		listeners.toArray(listenersArray);
		
		CallRecordingListener listener = (CallRecordingListener) listenersArray[0];
		
		template.sendEvent(new SampleEvent());
		
		assertEquals(1, listener.getNumberOfTimesInvoked());
	}
}
