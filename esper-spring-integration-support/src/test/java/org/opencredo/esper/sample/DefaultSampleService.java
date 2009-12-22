package org.opencredo.esper.sample;

import static org.opencredo.esper.sample.Status.*;

public class DefaultSampleService {

	private long sleepDuration;
	
	public void setSleepDuration(long sleepDuration) {
		this.sleepDuration = sleepDuration;
	}
	
	public void actionEvent(SampleEvent event) {
		event.setStatus(RECEIVED);
		
		event.setStatus(IN_PROGRESS);
		
		try {
			Thread.sleep(this.sleepDuration);
		} catch (InterruptedException e) {
			e.printStackTrace();
			
			event.setStatus(FAILED);
		}
		
		event.setStatus(COMPLETE);
	}
}
