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
