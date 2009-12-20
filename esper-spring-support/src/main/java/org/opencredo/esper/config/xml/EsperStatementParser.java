package org.opencredo.esper.config.xml;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedSet;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class EsperStatementParser extends AbstractBeanDefinitionParser {

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
				+ ".EsperStatement");

		Object query = element.getAttribute(EsperNamespaceUtils.STATEMENT_QUERY_ATTRIBUTE);

		builder.addConstructorArgValue(query);
		
		ManagedSet listeners = null;
		
		Element listenersElement = DomUtils.getChildElementByTagName(element, "listeners");
		if (listenersElement != null) {
			EsperListenerParser listenerParser = new EsperListenerParser();
			listeners = listenerParser.parseListeners(listenersElement, parserContext);
		}
		
		if (listeners != null) {
			builder.addPropertyValue("listeners", listeners);
		}
		
		return builder.getBeanDefinition();
	}
	
	@SuppressWarnings("unchecked")
	public ManagedSet parseStatements(Element element, ParserContext parserContext) {
		ManagedSet statements = new ManagedSet();
		NodeList childNodes = element.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			
			Node child = childNodes.item(i);
			
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				
				Element childElement = (Element) child;
				
				String localName = child.getLocalName();
				
				if ("statement".equals(localName)) {
					
					BeanDefinition definition = parserContext.getDelegate().parseCustomElement(childElement);
					statements.add(definition);
				} else if ("ref".equals(localName)) {
					String ref = childElement.getAttribute("bean");
					statements.add(new RuntimeBeanReference(ref));
				}
			}
		}
		return statements;
	}

}
