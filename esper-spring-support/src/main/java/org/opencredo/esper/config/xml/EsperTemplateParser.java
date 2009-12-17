package org.opencredo.esper.config.xml;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.util.ObjectUtils;
import org.w3c.dom.Element;

public class EsperTemplateParser extends AbstractSimpleBeanDefinitionParser {

	@Override
	protected boolean shouldGenerateId() {
		return false;
	}

	@Override
	protected boolean shouldGenerateIdAsFallback() {
		return true;
	}
	
	/*
	private static String[] referenceAttributes = new String[] { "channel" };

	@Override
	protected boolean isEligibleAttribute(String attributeName) {
		return !ObjectUtils.containsElement(referenceAttributes, attributeName)
				&& super.isEligibleAttribute(attributeName);
	}
	*/
	@Override
	protected String getBeanClassName(Element element) {
		return EsperNamespaceUtils.BASE_PACKAGE + ".EsperEngine";
	}
/*
	@Override
	protected void postProcess(BeanDefinitionBuilder builder,
			Element element) {
		for (String attributeName : referenceAttributes) {
			IntegrationNamespaceUtils.setReferenceIfAttributeDefined(builder, element, attributeName);
		}
	}
	
	*/
}
