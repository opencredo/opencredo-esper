/*
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package org.opencredo.esper.config.xml;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opencredo.esper.EsperStatement;
import org.opencredo.esper.EsperTemplate;
import org.opencredo.esper.sample.CallRecordingListener;
import org.opencredo.esper.sample.CallRecordingUnmatchedListener;
import org.opencredo.esper.sample.SampleEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class UnmatchedListenerEsperTemplateParserTest {

	@Autowired
	EsperTemplate template;
	
	@Autowired
	CallRecordingListener listener;
	
	@Autowired
	CallRecordingUnmatchedListener unmatchedlistener;
	
	@Test
	public void testTemplateInitializesWithOneStatement() {
		
		Set<EsperStatement> statements = template.getStatements();
		
		assertEquals(1, statements.size());	
	}
	
	@Test
	public void testSendSampleEvent() {
		
		Set<EsperStatement> statements = template.getStatements();
		
		assertEquals(1, statements.size());
		
		template.sendEvent(new SampleEvent());
		
		template.sendEvent("Simple string event!");
		
		assertEquals(1, listener.getNumberOfTimesInvoked());
		
		assertEquals(1, unmatchedlistener.getNumberOfTimesInvoked());
	}
}
