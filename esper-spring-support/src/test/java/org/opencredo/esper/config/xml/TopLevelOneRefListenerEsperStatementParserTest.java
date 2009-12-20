package org.opencredo.esper.config.xml;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opencredo.esper.EsperStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.espertech.esper.client.UpdateListener;

import static org.opencredo.esper.config.xml.EsperTestConstants.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TopLevelOneRefListenerEsperStatementParserTest {

	@Autowired
	EsperStatement statement;
	
	@Test
	public void testReferencedListenerRegistered() {
		assertNotNull(statement);
		assertEquals(EPL, statement.getEPL());
		
		Set<UpdateListener> listeners = statement.getListeners();
		assertEquals(1, listeners.size());
		
		UpdateListener[] listenersArray = new UpdateListener[listeners.size()];
		listeners.toArray(listenersArray);
		
		String className = listenersArray[0].getClass().getName();
		
		assertEquals(LISTENER_TEST_CLASS, className);
		
	}
}
