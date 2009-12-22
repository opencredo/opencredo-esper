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
}
