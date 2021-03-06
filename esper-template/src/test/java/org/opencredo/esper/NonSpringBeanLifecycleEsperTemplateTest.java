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

package org.opencredo.esper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opencredo.esper.sample.CallRecordingListener;
import org.opencredo.esper.sample.SampleEvent;
import org.opencredo.esper.spring.EsperTemplateBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Tests the {@link EsperTemplate} without it being the
 * {@link EsperTemplateBean}, so without the close integration with Spring
 * itself and its bean lifecycle hooks.
 * 
 * @author Russ Miles (russell.miles@opencredo.com)
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class NonSpringBeanLifecycleEsperTemplateTest {

    @Autowired
    EsperTemplate template;

    @Autowired
    CallRecordingListener listener;

    @Test
    public void testSendSampleEvent() {
        template.sendEvent(new SampleEvent());

        assertEquals(1, listener.getNumberOfTimesInvoked());
    }
}
