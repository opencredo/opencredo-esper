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

package org.opencredo.esper.samples.noodlebar.domain;

import static org.opencredo.esper.samples.noodlebar.domain.OrderStatus.*;

/**
 * Defines the Noodle Order itself.
 * 
 * @author Russ Miles (russ.miles@opencredo.com)
 *
 */
public class NoodleOrder {
	private OrderStatus status = NEW;
	
	public synchronized void setStatus(OrderStatus status) {
		this.status = status;
	}

	public OrderStatus getStatus() {
		return this.status;
	}
}
