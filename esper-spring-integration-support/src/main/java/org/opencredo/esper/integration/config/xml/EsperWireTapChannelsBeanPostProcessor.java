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
