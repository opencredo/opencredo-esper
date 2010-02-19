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

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.Assert;
import org.w3c.dom.Element;


public class InboundChannelAdapterParser extends AbstractSingleBeanDefinitionParser {

   private final static String BASE_PACKAGE_NAME = "org.opencredo.esper.integration";

	@Override
	protected String getBeanClassName(Element element) {
		return BASE_PACKAGE_NAME + ".EventDrivenEsperInboundChannelAdapter";
	}

	@Override
	protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        String channelRef = element.getAttribute("channel");
        Assert.hasText(channelRef, "channel attribute must be provided");
		builder.addConstructorArgReference(channelRef);
	}

}
