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
import org.springframework.util.StringUtils;

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
		
		initializeEsperTemplate(element, builder);
		
		initialiseSendContextProperty(element, builder);
		
		configureNotificationStrategy(element, builder);
		
		return builder.getBeanDefinition();
	}

	private void initializeEsperTemplate(Element element,
			BeanDefinitionBuilder builder) {
		String templateRef = element.getAttribute(EsperIntegrationNamespaceUtils.TEMPLATE_REF_ATTRIBUTE_NAME);
		
		builder.addConstructorArgReference(templateRef);
	}

	private void initialiseSendContextProperty(Element element,
			BeanDefinitionBuilder builder) {
		String sendContext = element.getAttribute(EsperIntegrationNamespaceUtils.SEND_CONTEXT_ATTRIBUTE_NAME);
		if ((sendContext != null) && (!"".equals(sendContext))) {
			builder.addPropertyValue("sendContext", Boolean.parseBoolean(sendContext));
		}
	}

	private void configureNotificationStrategy(Element element,
			BeanDefinitionBuilder builder) {
		String preSendFlag = element.getAttribute(EsperIntegrationNamespaceUtils.PRE_SEND_ATTRIBUTE_NAME);
		String postSendFlag = element.getAttribute(EsperIntegrationNamespaceUtils.POST_SEND_ATTRIBUTE_NAME);
		String preReceiveFlag = element.getAttribute(EsperIntegrationNamespaceUtils.PRE_RECEIVE_ATTRIBUTE_NAME);
		String postReceiveFlag = element.getAttribute(EsperIntegrationNamespaceUtils.POST_RECEIVE_ATTRIBUTE_NAME);
		
		if (StringUtils.hasText(preSendFlag) || StringUtils.hasText(postSendFlag) || StringUtils.hasText(preReceiveFlag) || StringUtils.hasText(postReceiveFlag)) {
			
			if (StringUtils.hasText(preSendFlag)) {
				builder.addPropertyValue("preSend", Boolean.parseBoolean(preSendFlag));
			} else {
				builder.addPropertyValue("preSend", false);
			}
			
			if (StringUtils.hasText(postSendFlag)) {
				builder.addPropertyValue("postSend", Boolean.parseBoolean(postSendFlag));
			} else {
				builder.addPropertyValue("postSend", false);
			}
			
			if (StringUtils.hasText(preReceiveFlag)) {
				builder.addPropertyValue("preReceive", Boolean.parseBoolean(preReceiveFlag));
			} else {
				builder.addPropertyValue("preReceive", false);
			}
			
			if (StringUtils.hasText(postReceiveFlag)) {
				builder.addPropertyValue("postReceive", Boolean.parseBoolean(postReceiveFlag));
			} else {
				builder.addPropertyValue("postReceive", false);
			}
		}
	}

}
