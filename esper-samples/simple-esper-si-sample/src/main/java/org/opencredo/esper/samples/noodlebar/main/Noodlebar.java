package org.opencredo.esper.samples.noodlebar.main;

import org.opencredo.esper.samples.noodlebar.domain.NoodleBar;
import org.opencredo.esper.samples.noodlebar.domain.NoodleOrder;
import org.opencredo.esper.samples.noodlebar.domain.yummy.YNoodleBar;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Noodlebar {

	
	private static NoodleBar noodleBar;
	
	private static final String NOODLE_BAR_BEAN_NAME = "noodleBar";
	
	public static void main(String[] args) {
		
		initializeDependencies();
		
		NoodleOrder order = new NoodleOrder();
		
		System.out.println("Before submitting the order the status is: " + order.getStatus());
		
		noodleBar.placeOrder(order);
		
		System.out.println("After completing the order the status is: " + order.getStatus());
		
	}
	
	private static void initializeDependencies() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:noodlebar-demo-context.xml");

		noodleBar = (NoodleBar) applicationContext
				.getBean(NOODLE_BAR_BEAN_NAME);
	}
	
}
