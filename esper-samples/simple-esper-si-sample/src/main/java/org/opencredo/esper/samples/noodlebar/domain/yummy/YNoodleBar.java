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

import org.opencredo.esper.samples.noodlebar.domain.NoodleOrder;

/**
 * Defines the interface for a noodle bar.
 * 
 * @author Russ Miles (russ.miles@opencredo.com)
 * 
 */
public interface YNoodleBar {

	/**
	 * Submit an order for cooking.
	 * 
	 * @param order
	 *            the order to be cooked.
	 */
	public void cookNoodles(NoodleOrder order);

	/**
	 * Submit an order and ask the service to call me back when my order is
	 * cooked.
	 * 
	 * @param order
	 *            the order to be cooked.
	 * @return the order that has been completed.
	 */
	public NoodleOrder cookNoodlesAndRingMeBack(NoodleOrder order);
}
