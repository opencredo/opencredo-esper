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

package org.opencredo.esper.samples.noodlebar.domain.yummy;

import static org.opencredo.esper.samples.noodlebar.domain.OrderStatus.*;

import org.opencredo.esper.samples.noodlebar.domain.NoodleOrder;

/**
 * 
 * A simple service that pretends to execute an order.
 * 
 * For the real thing, visit http://www.yummynoodlebar.co.uk/
 * 
 * @author Russ Miles (russ.miles@opencredo.com)
 *
 */
public class YummyNoodleBar implements YNoodleBar {
	
	private long cookDuration;
	
	public void setCookDuration(long cookDuration) {
		this.cookDuration = cookDuration;
	}
	
	public void cookNoodles(NoodleOrder order) {
		order.setStatus(RECEIVED);
		
		order.setStatus(BEING_COOKED);
		
		try {
			Thread.sleep(this.cookDuration);
		} catch (InterruptedException e) {
			e.printStackTrace();
			
			order.setStatus(REJECTED);
		}
		
		order.setStatus(COMPLETE);
	}
	
	public NoodleOrder cookNoodlesAndRingMeBack(NoodleOrder order) {
		order.setStatus(RECEIVED);
		
		order.setStatus(BEING_COOKED);
		
		try {
			Thread.sleep(this.cookDuration);
		} catch (InterruptedException e) {
			e.printStackTrace();
			
			order.setStatus(REJECTED);
		}
		
		order.setStatus(COMPLETE);
		
		return order;
	}
}
