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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TopLevelOneInnerBeanListenerEsperStatementParserTest {

	@Autowired
	EsperStatement statement;
	
	@Test
	public void testInnerBeanListenerRegistered() {
		assertNotNull(statement);
		assertEquals(EsperTestConstants.EPL, statement.getEPL());
		
		Set<UpdateListener> listeners = statement.getListeners();
		assertEquals(1, listeners.size());
	}
}
