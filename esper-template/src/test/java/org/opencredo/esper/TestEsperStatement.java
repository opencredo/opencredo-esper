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
import org.opencredo.esper.sample.SampleEvent;

import com.espertech.esper.client.EventBean;

public class TestEsperStatement {
	
	private EsperTemplate template;
	
	private EsperStatement statement;

	@Before
	public void setupTemplateAndStatement() throws Exception {
		this.template = new EsperTemplate();
		
		String epl = "select * from org.opencredo.esper.sample.SampleEvent";
		
		this.statement = new EsperStatement(epl);
		
		template.addStatement(statement);
		
		template.setBeanName("testTemplate");
		
		template.afterPropertiesSet();
	}
	
	@Test
	public void testConcurrencySafeQueryForObject() throws Exception {
		
		SampleEvent event = new SampleEvent();
		template.sendEvent(event);
		
		ParameterizedEsperRowMapper<SampleEvent> rm = new ParameterizedEsperRowMapper<SampleEvent>() {
			public SampleEvent mapRow(EventBean eventBean) {
				return (SampleEvent) eventBean.getUnderlying();
			}
		};
		SampleEvent result = statement.concurrentSafeQueryForObject(rm);
		
		assertEquals(result, event);
	}
	
	@Test
	public void testConcurrencyUnsafeQueryForObject() throws Exception {
		
		SampleEvent event = new SampleEvent();
		template.sendEvent(event);
		
		ParameterizedEsperRowMapper<SampleEvent> rm = new ParameterizedEsperRowMapper<SampleEvent>() {
			public SampleEvent mapRow(EventBean eventBean) {
				return (SampleEvent) eventBean.getUnderlying();
			}
		};
		SampleEvent result = statement.concurrentUnsafeQueryForObject(rm);
		
		assertEquals(result, event);
	}
	
	// TODO Continue to test each of the other pull methods.
	// Also test the Start and Stop operations (ensuring intermediate events are not sent using
	// the pull interface.

}
