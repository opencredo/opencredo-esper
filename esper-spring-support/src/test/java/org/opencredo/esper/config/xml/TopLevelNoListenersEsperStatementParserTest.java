package org.opencredo.esper.config.xml;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opencredo.esper.EsperStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TopLevelNoListenersEsperStatementParserTest {

	@Autowired
	EsperStatement statement;
	
	@Autowired
	@Qualifier("testStatement")
	EsperStatement namedStatement;
	
	@Test
	public void testStatementHasCorrectEpl() {
		assertEquals(EsperTestConstants.EPL, statement.getEPL());
	}
	
}
