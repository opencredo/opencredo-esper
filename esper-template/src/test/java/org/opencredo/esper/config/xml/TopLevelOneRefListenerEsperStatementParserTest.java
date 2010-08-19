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
