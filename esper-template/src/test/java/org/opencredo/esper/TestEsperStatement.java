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

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.opencredo.esper.sample.SampleEvent;

import com.espertech.esper.client.EventBean;

public class TestEsperStatement {
	
	private EsperTemplate template;
	
	private EsperStatement statement;
	
	private ParameterizedEsperRowMapper<SampleEvent> rowMapper;

	@Before
	public void setupTemplateStatementAndRowMapper() throws Exception {
		this.template = new EsperTemplate();
		
		String epl = "select * from org.opencredo.esper.sample.SampleEvent";
		
		this.statement = new EsperStatement(epl);
		
		template.addStatement(statement);
		
		template.setBeanName("testTemplate");
		
		template.afterPropertiesSet();
		
		this.rowMapper = new ParameterizedEsperRowMapper<SampleEvent>() {
			public SampleEvent mapRow(EventBean eventBean) {
				return (SampleEvent) eventBean.getUnderlying();
			}
		};
	}
	
	@Test
	public void testConcurrencySafeQueryForObject() throws Exception {
		
		SampleEvent event = new SampleEvent();
		template.sendEvent(event);
		
		SampleEvent result = statement.concurrentSafeQueryForObject(this.rowMapper);
		
		assertEquals(result, event);
	}
	
	@Test(expected=EsperStatementInvalidStateException.class)
	public void testConcurrencySafeQueryForObjectWhenStopped() throws Exception {
		
		statement.stop();
		
		SampleEvent event = new SampleEvent();
		
		template.sendEvent(event);
		
		statement.concurrentSafeQueryForObject(this.rowMapper);
		
	}
	
	@Test
	public void testConcurrencySafeQueryForObjectWhenStoppedAndRestarted() throws Exception {
		
		statement.stop();
		statement.start();
		
		SampleEvent event = new SampleEvent();
		
		template.sendEvent(event);
		
		SampleEvent result = statement.concurrentSafeQueryForObject(this.rowMapper);
		
		assertEquals(result, event);
		
	}
	
	@Test
	public void testConcurrencyUnsafeQueryForObject() throws Exception {
		
		SampleEvent event = new SampleEvent();
		template.sendEvent(event);
		
		
		SampleEvent result = statement.concurrentUnsafeQueryForObject(this.rowMapper);
		
		assertEquals(result, event);
		
	}
	
	@Test(expected=EsperStatementInvalidStateException.class)
	public void testConcurrencyUnsafeQueryForObjectWhenStopped() throws Exception {
		
		statement.stop();
		
		SampleEvent event = new SampleEvent();
		
		template.sendEvent(event);
		
		statement.concurrentUnsafeQueryForObject(this.rowMapper);
		
	}
	
	@Test
	public void testConcurrencyUnsafeQueryForObjectWhenStoppedAndRestarted() throws Exception {
		
		statement.stop();
		statement.start();
		
		SampleEvent event = new SampleEvent();
		
		template.sendEvent(event);
		
		SampleEvent result = statement.concurrentUnsafeQueryForObject(this.rowMapper);
		
		assertEquals(result, event);
		
	}
	
	@Test
	public void testConcurrencySafeQuery() throws Exception {
		
		SampleEvent event = new SampleEvent();
		template.sendEvent(event);
		
		
		List<SampleEvent> events = statement.concurrentSafeQuery(this.rowMapper);
		
		assertEquals(events.size(), 1);
		assertEquals(events.get(0), event);
		
	}
	
	@Test(expected=EsperStatementInvalidStateException.class)
	public void testConcurrencySafeQueryWhenStopped() throws Exception {
		
		statement.stop();
		
		SampleEvent event = new SampleEvent();
		
		template.sendEvent(event);
		
		statement.concurrentSafeQuery(this.rowMapper);
		
	}
	
	@Test
	public void testConcurrencySafeQueryWhenStoppedAndRestarted() throws Exception {
		
		statement.stop();
		statement.start();
		
		SampleEvent event = new SampleEvent();
		
		template.sendEvent(event);
		
		List<SampleEvent> events = statement.concurrentSafeQuery(this.rowMapper);
		
		assertEquals(events.size(), 1);
		assertEquals(events.get(0), event);
		
	}
	
	@Test
	public void testConcurrencyUnsafeQuery() throws Exception {
		
		SampleEvent event = new SampleEvent();
		template.sendEvent(event);
		
		
		List<SampleEvent> events = statement.concurrentUnsafeQuery(this.rowMapper);
		
		assertEquals(events.size(), 1);
		assertEquals(events.get(0), event);
	}
	
	@Test(expected=EsperStatementInvalidStateException.class)
	public void testConcurrencyUnsafeQueryWhenStopped() throws Exception {
		
		statement.stop();
		
		SampleEvent event = new SampleEvent();
		
		template.sendEvent(event);
		
		statement.concurrentUnsafeQuery(this.rowMapper);
		
	}
	
	@Test
	public void testConcurrencyUnsafeQueryWhenStoppedAndRestarted() throws Exception {
		
		statement.stop();
		statement.start();
		
		SampleEvent event = new SampleEvent();
		
		template.sendEvent(event);
		
		List<SampleEvent> events = statement.concurrentUnsafeQuery(this.rowMapper);
		
		assertEquals(events.size(), 1);
		assertEquals(events.get(0), event);
		
	}

}
