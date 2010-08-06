/*
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
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
