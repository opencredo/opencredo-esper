package org.opencredo.esper.config.xml;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.ManagedSet;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class EsperListenerParser {

	@SuppressWarnings("unchecked")
	public ManagedSet parseListeners(Element element, ParserContext parserContext) {
		ManagedSet listeners = new ManagedSet();
		NodeList childNodes = element.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node child = childNodes.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				Element childElement = (Element) child;
				String localName = child.getLocalName();
				if ("bean".equals(localName)) {
					BeanDefinitionHolder holder = parserContext.getDelegate().parseBeanDefinitionElement(childElement);
					parserContext.registerBeanComponent(new BeanComponentDefinition(holder));
					listeners.add(new RuntimeBeanReference(holder.getBeanName()));
				}
				else if ("ref".equals(localName)) {
					String ref = childElement.getAttribute("bean");
					listeners.add(new RuntimeBeanReference(ref));
				}
			}
		}
		return listeners;
	}

}
