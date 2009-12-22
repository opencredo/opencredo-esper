package org.opencredo.esper.sample;

import static org.opencredo.esper.sample.Status.*;

public class SampleEvent {
	private Status status = NEW;
	
	public synchronized void setStatus(Status status) {
		this.status = status;
	}

	public Status getStatus() {
		return this.status;
	}
}
