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

package org.opencredo.esper.samples.noodlebar.main;

import org.opencredo.esper.samples.noodlebar.domain.NoodleBar;
import org.opencredo.esper.samples.noodlebar.domain.NoodleOrder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Submits orders to a noodle bar with no additional esper-related
 * configuration. This is to demonstrates the starting point before OpenCredo
 * Esper can be applied transparently to expose temporal information about the
 * underlying Spring Integration infrastructure.
 * 
 * @author Russ Miles (russ.miles@opencredo.com)
 * 
 */
public class DirectNoodleBarOrderGenerator {

	private static NoodleBar noodleBar;

	private static final String NOODLE_BAR_BEAN_NAME = "noodleBar";

	public static void main(String[] args) {

		initializeDependencies();

		NoodleOrder order = new NoodleOrder();

		System.out.println("Before submitting the order the status is: "
				+ order.getStatus());

		noodleBar.placeOrder(order);

		System.out.println("After completing the order the status is: "
				+ order.getStatus());

	}

	private static void initializeDependencies() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:noodlebar-demo-context.xml");

		noodleBar = (NoodleBar) applicationContext
				.getBean(NOODLE_BAR_BEAN_NAME);
	}

}
