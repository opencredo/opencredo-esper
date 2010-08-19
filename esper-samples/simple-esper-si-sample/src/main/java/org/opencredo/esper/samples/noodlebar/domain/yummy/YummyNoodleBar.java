/*
 * OpenCredo-Esper - simplifies adopting Esper in Java applications. 
 * Copyright (C) 2010  OpenCredo Ltd.
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.opencredo.esper.samples.noodlebar.domain.yummy;

import static org.opencredo.esper.samples.noodlebar.domain.OrderStatus.*;

import org.opencredo.esper.samples.noodlebar.domain.NoodleOrder;

/**
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
