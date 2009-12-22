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
public class MultipleInnerStatementEsperTemplateParserTest {

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
