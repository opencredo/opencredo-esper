/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opencredo.esper.samples.noodlebar.domain.throughput;

import org.opencredo.esper.samples.noodlebar.throughput.ThroughputMonitor;

public class NoodleOrderThroughputMonitor implements ThroughputMonitor {
	private long averageThroughput = 0l;

	public synchronized void receiveCurrentThroughput(long ordersProcessedPerDuration) {
		this.averageThroughput = ordersProcessedPerDuration;
	}

	public synchronized long getAverageThroughput() {
		return averageThroughput;
	}
}
