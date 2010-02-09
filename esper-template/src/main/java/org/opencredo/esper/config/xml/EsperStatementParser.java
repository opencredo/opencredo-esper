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

package org.opencredo.esper.config.xml;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedSet;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses an esper-statement element.
 * 
 * @author Russ Miles (russ.miles@opencredo.com)
 *
 */
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
			if (listeners != null) {
				builder.addPropertyValue("listeners", listeners);
			} else {
				parserContext.getReaderContext().error(
						"At least one 'listener' should be provided.", listenersElement);
			}
		} else {
			
			Element subscriberElement = DomUtils.getChildElementByTagName(element, "subscriber");
			if (subscriberElement != null) {
				String subscriberRef = (String) subscriberElement.getAttribute("ref");
				if (StringUtils.hasText(subscriberRef)) {
					builder.addPropertyReference("subscriber", subscriberRef);
				}
			} else {
				parserContext.getReaderContext().error(
						"At least one of 'listeners' or 'subscriber' should be provided.", element);
			}
		}
		
		return builder.getBeanDefinition();
	}
	
	/**
	 * Parses out the individual statement elements for further processing.
	 * 
	 * @param element the esper-template context
	 * @param parserContext the parser's context
	 * @return a set of initialized esper statements
	 */
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
