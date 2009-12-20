package org.opencredo.esper.config.xml;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedSet;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public class EsperTemplateParser extends AbstractBeanDefinitionParser {

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
		.genericBeanDefinition(EsperNamespaceUtils.BASE_PACKAGE
				+ ".EsperTemplate");

		ManagedSet statements = null;
		
		Element statementsElement = DomUtils.getChildElementByTagName(element, "statements");
		if (statementsElement != null) {
			EsperStatementParser statementParser = new EsperStatementParser();
			statements = statementParser.parseStatements(statementsElement, parserContext);
		}
		
		if (statements != null) {
			builder.addPropertyValue("statements", statements);
		}
		
		return builder.getBeanDefinition();
	}
/*
	@Override
	protected boolean shouldGenerateId() {
		return false;
	}

	@Override
	protected boolean shouldGenerateIdAsFallback() {
		return true;
	}
	*/
	
	/*
	private static String[] referenceAttributes = new String[] { "channel" };

	@Override
	protected boolean isEligibleAttribute(String attributeName) {
		return !ObjectUtils.containsElement(referenceAttributes, attributeName)
				&& super.isEligibleAttribute(attributeName);
	}
	*/
	/*
	@Override
	protected String getBeanClassName(Element element) {
		return EsperNamespaceUtils.BASE_PACKAGE + ".EsperTemplate";
	}
	*/
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
