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
		
		return builder.getBeanDefinition();
	}

}
