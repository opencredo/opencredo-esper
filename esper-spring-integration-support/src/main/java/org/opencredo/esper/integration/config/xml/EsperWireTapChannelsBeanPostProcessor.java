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
