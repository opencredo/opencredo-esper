package org.opencredo.esper.sample;

public interface ThroughputMonitor {
	public void receiveCurrentThroughput(long eventsProcessedPerDuration);
	
	public long getAverageThroughput();
}
