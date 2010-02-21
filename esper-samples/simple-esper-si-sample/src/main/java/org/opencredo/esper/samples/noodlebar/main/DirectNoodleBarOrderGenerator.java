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
