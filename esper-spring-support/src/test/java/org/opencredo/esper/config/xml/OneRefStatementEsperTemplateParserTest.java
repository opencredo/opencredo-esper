package org.opencredo.esper.config.xml;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opencredo.esper.EsperStatement;
import org.opencredo.esper.EsperTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class OneRefStatementEsperTemplateParserTest {

	@Autowired
	EsperTemplate template;
	
	@Test
	public void testTemplateInitializesWithOneStatment() {
		
		Set<EsperStatement> statements = template.getStatements();
		
		assertEquals(1, statements.size());
		
	}
}
