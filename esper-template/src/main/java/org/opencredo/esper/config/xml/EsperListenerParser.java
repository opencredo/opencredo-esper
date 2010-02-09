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

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.ManagedSet;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses the listeners block in the Spring custom namespace support.
 * 
 * @author Russ Miles (russ.miles@opencredo.com)
 *
 */
public class EsperListenerParser {

	/**
	 * Parses out a set of configured esper statement listeners.
	 * 
	 * @param element the esper listeners element
	 * @param parserContext the parser's current context
	 * @return a list of configured esper statement listeners
	 */
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
