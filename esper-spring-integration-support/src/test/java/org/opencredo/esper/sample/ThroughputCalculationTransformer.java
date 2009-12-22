package org.opencredo.esper.sample;

import org.springframework.integration.annotation.Transformer;

import com.espertech.esper.client.EventBean;

public class ThroughputCalculationTransformer {
	private String throughputCalculationKey = "throughput";
	
	public void setThroughputCalculationKey(String throughputCalculationKey) {
		this.throughputCalculationKey = throughputCalculationKey;
	}

	@Transformer
	public Object unpackThroughputCalculationFromEsperBeanArray(EventBean[] eventBeans) throws ThroughputCalculationException {
		if (eventBeans.length > 0) {
			Object throughputCalculation = eventBeans[0].get(throughputCalculationKey);
			return throughputCalculation;
		}
		throw new ThroughputCalculationException("Could not find specified throughput calculation property in event beans");
	}
}
