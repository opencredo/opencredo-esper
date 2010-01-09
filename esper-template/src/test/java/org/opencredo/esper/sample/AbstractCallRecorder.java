package org.opencredo.esper.sample;

public abstract class AbstractCallRecorder {
	private int numberOfTimesInvoked = 0;

	public int getNumberOfTimesInvoked() {
		return numberOfTimesInvoked;
	}
	
	protected void incrementCallCounter() {
		this.numberOfTimesInvoked++;
	}
}
