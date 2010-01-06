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

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public class EsperWireTapChannelsParser extends AbstractSingleBeanDefinitionParser {

	private final static String BASE_PACKAGE_NAME = "org.opencredo.esper.integration";

	@Override
	protected String getBeanClassName(Element element) {
		return BASE_PACKAGE_NAME + ".config.xml.EsperWireTapChannelsBeanPostProcessor";
	}
	
	@Override
	protected boolean shouldGenerateId() {
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
		
		String defaultWireTapRef = element.getAttribute("default-wire-tap");
		
		List<Element> channelElements = (List<Element>) DomUtils.getChildElementsByTagName(element, "channel");
		
		ManagedMap channelPatternMappings = new ManagedMap();
		
		for (Element channelElement : channelElements) {
			
			Pattern pattern = Pattern.compile(channelElement.getAttribute("pattern"));
			
			String wireTapRef = channelElement.getAttribute("wire-tap");
			
			if (!StringUtils.hasText(wireTapRef) && !StringUtils.hasText(defaultWireTapRef)) {
				parserContext.getReaderContext().error(
						"At least one of 'default-wire-tap' or 'wire-tap' must be provided.", channelElement);
			}
			
			if (StringUtils.hasText(wireTapRef)) {
				channelPatternMappings.put(pattern, new RuntimeBeanReference(wireTapRef));
			} else {
				channelPatternMappings.put(pattern, new RuntimeBeanReference(defaultWireTapRef));
			}
		
		}
		
		builder.addConstructorArgValue(channelPatternMappings);
	}
	
}
