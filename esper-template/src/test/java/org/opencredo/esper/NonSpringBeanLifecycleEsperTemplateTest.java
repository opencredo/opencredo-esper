package org.opencredo.esper;

import static org.junit.Assert.assertEquals;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.opencredo.esper.EsperTemplate;
import org.opencredo.esper.sample.CallRecordingListener;
import org.opencredo.esper.sample.SampleEvent;
import org.opencredo.esper.spring.EsperTemplateBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Tests the {@link EsperTemplate} without it being 
 * the {@link EsperTemplateBean}, so without the close integration
 * with Spring itself and its bean lifecycle hooks.
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
