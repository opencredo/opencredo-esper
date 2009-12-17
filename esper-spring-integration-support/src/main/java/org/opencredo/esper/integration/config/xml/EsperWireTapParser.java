package org.opencredo.esper.integration.config.xml;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.BeanDefinitionRegisteringParser;
import org.w3c.dom.Element;

public class EsperWireTapParser implements BeanDefinitionRegisteringParser {

	public String parse(Element element, ParserContext parserContext) {

		BeanDefinitionBuilder builder = BeanDefinitionBuilder
				.genericBeanDefinition(EsperIntegrationNamespaceUtils.BASE_PACKAGE
						+ ".channel.interceptor.EsperWireTap");

		
		return null;
	}

}
