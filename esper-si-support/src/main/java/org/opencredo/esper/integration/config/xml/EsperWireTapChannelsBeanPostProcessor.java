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

package org.opencredo.esper.integration.config.xml;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.opencredo.esper.integration.interceptor.EsperWireTap;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.integration.channel.AbstractMessageChannel;
import org.springframework.util.Assert;

public class EsperWireTapChannelsBeanPostProcessor implements BeanPostProcessor {
	
	@SuppressWarnings("unchecked")
	private final Map channelPatternMappings;

	@SuppressWarnings("unchecked")
	public EsperWireTapChannelsBeanPostProcessor(Map channelPatternMappings) {
		Assert.notNull(channelPatternMappings, "channelPatternMappings must not be null");
		this.channelPatternMappings = channelPatternMappings;
	}


	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof AbstractMessageChannel) {
			this.addMatchingWireTaps((AbstractMessageChannel) bean);
		}
		
		return bean;
	}

	@SuppressWarnings("unchecked")
	private void addMatchingWireTaps(AbstractMessageChannel channel) {
		
		Assert.notNull(channel.getName(), "channel name must not be null");
		
		Set<Entry> patternWireTapEntries = this.channelPatternMappings.entrySet();
		
		for (Entry patternWireTapEntry : patternWireTapEntries) {
			if (((Pattern) patternWireTapEntry.getKey()).matcher(channel.getName()).matches()) {
				channel.addInterceptor((EsperWireTap) patternWireTapEntry.getValue());
			}
		}
	}
}
