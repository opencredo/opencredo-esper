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

package org.opencredo.esper;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.opencredo.esper.sample.CallRecordingListener;
import org.opencredo.esper.sample.SampleEvent;

/**
 * A unit test for the {@link EsperTemplate}.
 * 
 * @author Russ Miles (russ.miles@opencredo.com)
 * 
 */
public class TestEsperTemplate {
	
	private EsperTemplate template;

	@Before
	public void setupStatementUnderTest() {
		this.template = new EsperTemplate();
	}
	
	@Test(expected = InvalidEsperConfigurationException.class)
	public void testExceptionRaisedWhenTemplateNotInitialized() {
		template.sendEvent(new SampleEvent());
	}
	
	@Test
	public void testEventWithNoStatements() {
		setupTemplateAndSendSampleEvent();
	}

	private void setupTemplateAndSendSampleEvent() {
		template.setName("testTemplate");
		
		template.initialize();
		
		template.sendEvent(new SampleEvent());
	}
	
	@Test
	public void testEventWithOneStatementNoListener() {
		
		EsperStatement statement = addTestStatement();
		
		template.addStatement(statement);
		
		setupTemplateAndSendSampleEvent();
	}
	
	@Test
	public void testEventWithOneStatementWithOneListener() {
		
		EsperStatement statement = addTestStatement();
		
		CallRecordingListener listener = this.addListenerToStatement(statement);
		
		template.addStatement(statement);
		
		setupTemplateAndSendSampleEvent();
		
		assertEquals(1, listener.getNumberOfTimesInvoked());
	}
	
	
	@Test
	public void testAddStatementAfterInitialisation() {
		setupTemplateAndSendSampleEvent();
		EsperStatement statement = addTestStatement();
    	CallRecordingListener listener = this.addListenerToStatement(statement);
     	template.addStatement(statement);
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
