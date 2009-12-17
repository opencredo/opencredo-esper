package org.opencredo.esper;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

/**
 * A listener used for testing and sample purposes.
 * 
 * @author Russ Miles (russell.miles@opencredo.com)
 * 
 */
public class CallRecordingListener implements UpdateListener {
	
	private boolean called = false;
	
	public boolean getCalled() {
		return this.called;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.espertech.esper.client.UpdateListener#update(com.espertech.esper.
	 * client.EventBean[], com.espertech.esper.client.EventBean[])
	 */
	public void update(EventBean[] eventBeans, EventBean[] eventBeans1) {
		this.called = true;
	}
}
