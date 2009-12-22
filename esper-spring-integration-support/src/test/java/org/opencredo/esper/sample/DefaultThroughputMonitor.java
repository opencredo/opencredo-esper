package org.opencredo.esper.sample;

public class DefaultThroughputMonitor implements ThroughputMonitor {
	private long averageThroughput = 0l;

	public synchronized void receiveCurrentThroughput(long eventsProcessedPerDuration) {
		this.averageThroughput = eventsProcessedPerDuration;
	}

	public synchronized long getAverageThroughput() {
		return averageThroughput;
	}
}
