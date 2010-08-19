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

package org.opencredo.esper.integration.config.xml;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public class EsperWireTapChannelsParser extends AbstractSingleBeanDefinitionParser {

    private final static String BASE_PACKAGE_NAME = "org.opencredo.esper.integration";

    @Override
    protected String getBeanClassName(Element element) {
        return BASE_PACKAGE_NAME + ".config.xml.EsperWireTapChannelsBeanPostProcessor";
    }

    @Override
    protected boolean shouldGenerateId() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {

        String defaultWireTapRef = element.getAttribute("default-wire-tap");

        List<Element> channelElements = (List<Element>) DomUtils.getChildElementsByTagName(element, "channel");

        ManagedMap channelPatternMappings = new ManagedMap();

        for (Element channelElement : channelElements) {

            Pattern pattern = Pattern.compile(channelElement.getAttribute("pattern"));

            String wireTapRef = channelElement.getAttribute("wire-tap");

            if (!StringUtils.hasText(wireTapRef) && !StringUtils.hasText(defaultWireTapRef)) {
                parserContext.getReaderContext().error(
                        "At least one of 'default-wire-tap' or 'wire-tap' must be provided.", channelElement);
            }

            if (StringUtils.hasText(wireTapRef)) {
                channelPatternMappings.put(pattern, new RuntimeBeanReference(wireTapRef));
            } else {
                channelPatternMappings.put(pattern, new RuntimeBeanReference(defaultWireTapRef));
            }

        }

        builder.addConstructorArgValue(channelPatternMappings);
    }
    /*
     * private void initializeEsperNotificationStrategy(Element element,
     * BeanDefinitionBuilder builder) {
     * 
     * EsperWireTapStrategyInterceptor interceptor = new
     * EsperWireTapStrategyInterceptor();
     * 
     * interceptor.setPreSend(Boolean.parseBoolean(element.getAttribute(
     * EsperIntegrationNamespaceUtils.PRE_SEND_ATTRIBUTE_NAME)));
     * interceptor.setPostSend
     * (Boolean.parseBoolean(element.getAttribute(EsperIntegrationNamespaceUtils
     * .POST_SEND_ATTRIBUTE_NAME)));
     * interceptor.setPreReceive(Boolean.parseBoolean
     * (element.getAttribute(EsperIntegrationNamespaceUtils
     * .PRE_RECEIVE_ATTRIBUTE_NAME)));
     * interceptor.setPostReceive(Boolean.parseBoolean
     * (element.getAttribute(EsperIntegrationNamespaceUtils
     * .POST_RECEIVE_ATTRIBUTE_NAME)));
     * 
     * builder.addConstructorArgValue(interceptor); }
     */
}
