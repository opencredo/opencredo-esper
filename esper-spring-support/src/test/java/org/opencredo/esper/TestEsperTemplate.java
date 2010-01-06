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

package org.opencredo.esper;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.opencredo.esper.sample.CallRecordingListener;
import org.opencredo.esper.sample.SampleEvent;

public class TestEsperTemplate {

	
	private EsperTemplate template;

	@Before
	public void setupStatementUnderTest() {
		this.template = new EsperTemplate();
	}
	
	@Test
	public void testEventWithNoStatements() throws Exception {
		template.setBeanName("testTemplate");
		
		template.afterPropertiesSet();
		
		template.sendEvent(new SampleEvent());
	}
	
	@Test
	public void testEventWithOneStatementNoListener() throws Exception {
		
		EsperStatement statement = addTestStatement();
		
		template.addStatement(statement);
		
		template.setBeanName("testTemplate");
		
		template.afterPropertiesSet();
		
		template.sendEvent(new SampleEvent());
	}
	
	@Test
	public void testEventWithOneStatementWithOneListener() throws Exception {
		
		EsperStatement statement = addTestStatement();
		
		CallRecordingListener listener = this.addListenerToStatement(statement);
		
		template.addStatement(statement);
		
		template.setBeanName("testTemplate");
		
		template.afterPropertiesSet();
		
		template.sendEvent(new SampleEvent());
		
		assertEquals(1, listener.getNumberOfTimesInvoked());
	}

	private CallRecordingListener addListenerToStatement(EsperStatement statement) {
		CallRecordingListener listener = new CallRecordingListener();
		
		statement.addListener(listener);
		
		return listener;
	}

	private EsperStatement addTestStatement() {
		
		String epl = "select * from org.opencredo.esper.sample.SampleEvent";
		
		return new EsperStatement(epl);
	}
}
