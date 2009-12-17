package org.opencredo.esper.integration.config.xml;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.util.ObjectUtils;
import org.w3c.dom.Element;

public class InboundGatewayParser extends AbstractSimpleBeanDefinitionParser {

	private static String[] referenceAttributes = new String[] { "channel" };

	@Override
	protected boolean isEligibleAttribute(String attributeName) {
		return !ObjectUtils.containsElement(referenceAttributes, attributeName)
				&& super.isEligibleAttribute(attributeName);
	}
	
	@Override
	protected String getBeanClassName(Element element) {
		return EsperIntegrationNamespaceUtils.BASE_PACKAGE + ".gateway.EsperInboundGateway";
	}

	@Override
	protected void postProcess(BeanDefinitionBuilder builder,
			Element element) {
		for (String attributeName : referenceAttributes) {
			IntegrationNamespaceUtils.setReferenceIfAttributeDefined(builder, element, attributeName);
		}
	}
}
