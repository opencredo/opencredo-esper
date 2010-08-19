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

package org.opencredo.esper.integration.config.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opencredo.esper.EsperTemplate;
import org.opencredo.esper.integration.MessageContext;
import org.opencredo.esper.sample.CallRecordingListener;
import org.opencredo.esper.sample.CallRecordingUnmatchedListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class UnmatchedListenerInboundChannelAdapterParserTest {

    @Autowired
    EsperTemplate template;

    @Autowired
    CallRecordingListener listener;

    @Autowired
    CallRecordingUnmatchedListener unmatchedListener;

    @Test
    public void sendAnEsperContextMessageAndAssertThatListenerIsInvoked() {
        template.sendEvent(new MessageContext(new DirectChannel(), "testSourceId"));

        template.sendEvent("A simple string event object!");

        assertEquals(1, listener.getNumberOfTimesInvoked());

        assertEquals(1, unmatchedListener.getNumberOfTimesInvoked());
    }
}
