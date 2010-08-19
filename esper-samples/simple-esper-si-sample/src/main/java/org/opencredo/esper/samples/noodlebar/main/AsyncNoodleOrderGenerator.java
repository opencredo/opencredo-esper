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
import org.opencredo.esper.samples.noodlebar.domain.OrderStatus;
import org.opencredo.esper.samples.noodlebar.domain.throughput.NoodleOrderThroughputMonitor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Asynchronously submits a number of orders to a {@link NoodleBar}
 * 
 * @author Russ Miles (russ.miles@opencredo.com)
 *
 */
public class AsyncNoodleOrderGenerator {

	private static final int NUMBER_OF_ORDERS = 1000;

	private static final String NOODLE_BAR_BEAN_NAME = "noodleBar";

	private static final String NOODLE_ORDER_THROUGHPUT_MONITOR_BEAN_NAME = "noodleOrderThroughputMonitor";

	private static NoodleBar noodleBar;

	private static NoodleOrderThroughputMonitor noodleOrderThroughputMonitor;

	public static void main(String[] args) {

		System.out.println("Initializing Dependencies...");
		
		initializeDependencies();

		long startTime = System.currentTimeMillis();

		System.out.println("Sending orders into the Noodle Bar...");
		
		sendSomeOrders();

		long stopTime = System.currentTimeMillis();

		long timeTaken = stopTime - startTime;

		System.out.println("Simple timer calculated at end " + timeTaken
				+ " milliseconds for " + NUMBER_OF_ORDERS + " orders");

		System.out
				.println("The Noodle Bar is actually accepting "
						+ noodleOrderThroughputMonitor.getAverageThroughput()
						+ " orders per second according to continuous Esper Monitoring");
		
		System.exit(0);
	}

	private static void sendSomeOrders() {
		NoodleOrder[] orders = new NoodleOrder[NUMBER_OF_ORDERS];

		// Create and send events
		for (int x = 0; x < NUMBER_OF_ORDERS; x++) {
			orders[x] = new NoodleOrder();
			noodleBar.placeOrder(orders[x]);
			// Sleep a little bit between orders
			try {
				Thread.sleep(5l);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		boolean processing = true;
		while (processing) {
			processing = false;
			for (NoodleOrder order : orders) {
				if (order.getStatus() == OrderStatus.COMPLETE) {
					System.out.println("Order Completed!");
				} else {
					processing = true;
				}
			}
			try {
				Thread.sleep(20l);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static void initializeDependencies() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:async-noodlebar-context.xml");

		noodleBar = (NoodleBar) applicationContext
				.getBean(NOODLE_BAR_BEAN_NAME);

		noodleOrderThroughputMonitor = (NoodleOrderThroughputMonitor) applicationContext
				.getBean(NOODLE_ORDER_THROUGHPUT_MONITOR_BEAN_NAME);
	}
}
