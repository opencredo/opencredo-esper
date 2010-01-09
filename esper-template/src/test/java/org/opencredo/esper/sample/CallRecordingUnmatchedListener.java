package org.opencredo.esper.sample;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UnmatchedListener;

public class CallRecordingUnmatchedListener extends AbstractCallRecorder implements UnmatchedListener {

	public void update(EventBean arg0) {
		super.incrementCallCounter();
	}
}
