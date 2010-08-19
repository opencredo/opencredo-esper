/*
 * OpenCredo-Esper - simplifies adopting Esper in Java applications. 
 * Copyright (C) 2010  OpenCredo Ltd.
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
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
		
		template.setName("testTemplate");
		
		template.initialize();
		
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
