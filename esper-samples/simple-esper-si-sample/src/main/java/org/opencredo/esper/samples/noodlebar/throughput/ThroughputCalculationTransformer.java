/*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 3
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/

package org.opencredo.esper.samples.noodlebar.throughput;

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
