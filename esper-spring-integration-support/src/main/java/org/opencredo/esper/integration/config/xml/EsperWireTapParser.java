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
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;

public class EsperWireTapParser extends AbstractBeanDefinitionParser {

	@Override
	protected boolean shouldGenerateId() {
		return false;
	}

	@Override
	protected boolean shouldGenerateIdAsFallback() {
		return true;
	}
	
	@Override
	protected AbstractBeanDefinition parseInternal(Element element,
			ParserContext parserContext) {
		
		BeanDefinitionBuilder builder = BeanDefinitionBuilder
		.genericBeanDefinition(EsperIntegrationNamespaceUtils.BASE_PACKAGE
				+ ".interceptor.EsperWireTap");
		
		String templateRef = element.getAttribute(EsperIntegrationNamespaceUtils.TEMPLATE_REF_ATTRIBUTE_NAME);
		
		builder.addPropertyReference("template", templateRef);
		
		setSendContextProperty(element, builder);
		
		return builder.getBeanDefinition();
	}

	private void setSendContextProperty(Element element,
			BeanDefinitionBuilder builder) {
		String sendContext = element.getAttribute(EsperIntegrationNamespaceUtils.SEND_CONTEXT_ATTRIBUTE_NAME);
		if ((sendContext != null) && (!"".equals(sendContext))) {
			builder.addPropertyValue("sendContext", Boolean.parseBoolean(sendContext));
		}
	}

}
