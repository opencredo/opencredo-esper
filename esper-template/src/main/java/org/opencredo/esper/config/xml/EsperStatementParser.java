/*
 * OpenCredo-Esper - simplifies adopting Esper in Java applications. 
 * Copyright (C) 2010  OpenCredo Ltd.
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
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
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(EsperNamespaceUtils.BASE_PACKAGE
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
                parserContext.getReaderContext().error("At least one 'listener' should be provided.", listenersElement);
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
     * @param element
     *            the esper-template context
     * @param parserContext
     *            the parser's context
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
