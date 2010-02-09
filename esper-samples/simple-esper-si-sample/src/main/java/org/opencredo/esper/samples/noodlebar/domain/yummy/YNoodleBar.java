package org.opencredo.esper.samples.noodlebar.domain.yummy;

import org.opencredo.esper.samples.noodlebar.domain.NoodleOrder;

public interface YNoodleBar {
	public void cookNoodles(NoodleOrder order);

	
	public NoodleOrder cookNoodlesAndRingMeBack(NoodleOrder order);
}
