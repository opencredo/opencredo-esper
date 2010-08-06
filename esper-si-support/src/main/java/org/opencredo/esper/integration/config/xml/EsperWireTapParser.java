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

package org.opencredo.esper.integration.config.xml;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.Assert;
import org.w3c.dom.Element;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.util.StringUtils;

public class EsperWireTapParser extends AbstractBeanDefinitionParser {

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
                .genericBeanDefinition(EsperIntegrationNamespaceUtils.BASE_PACKAGE
                        + ".interceptor.EsperWireTap");

        initializeEsperTemplate(element, builder);

        setSourceId(element, builder);

        initialiseSendContextProperty(element, builder);

        configureNotificationStrategy(element, builder);

        return builder.getBeanDefinition();
    }

    private void initializeEsperTemplate(Element element,
                                         BeanDefinitionBuilder builder) {
        String templateRef = element.getAttribute(EsperIntegrationNamespaceUtils.TEMPLATE_REF_ATTRIBUTE_NAME);

        builder.addConstructorArgReference(templateRef);
    }

    private void initialiseSendContextProperty(Element element,
                                               BeanDefinitionBuilder builder) {
        String sendContext = element.getAttribute(EsperIntegrationNamespaceUtils.SEND_CONTEXT_ATTRIBUTE_NAME);
        if ((sendContext != null) && (!"".equals(sendContext))) {
            builder.addPropertyValue("sendContext", Boolean.parseBoolean(sendContext));
        }
    }

    private void setSourceId(Element element, BeanDefinitionBuilder builder) {

        String sourceId = element.getAttribute("sourceId");
        Assert.hasText(sourceId, "sourceId attribute is required");
        builder.addConstructorArgValue(sourceId);

    }

    private void configureNotificationStrategy(Element element,
                                               BeanDefinitionBuilder builder) {
        String preSendFlag = element.getAttribute(EsperIntegrationNamespaceUtils.PRE_SEND_ATTRIBUTE_NAME);
        String postSendFlag = element.getAttribute(EsperIntegrationNamespaceUtils.POST_SEND_ATTRIBUTE_NAME);
        String preReceiveFlag = element.getAttribute(EsperIntegrationNamespaceUtils.PRE_RECEIVE_ATTRIBUTE_NAME);
        String postReceiveFlag = element.getAttribute(EsperIntegrationNamespaceUtils.POST_RECEIVE_ATTRIBUTE_NAME);

        if (StringUtils.hasText(preSendFlag) || StringUtils.hasText(postSendFlag) || StringUtils.hasText(preReceiveFlag) || StringUtils.hasText(postReceiveFlag)) {

            if (StringUtils.hasText(preSendFlag)) {
                builder.addPropertyValue("preSend", Boolean.parseBoolean(preSendFlag));
            } else {
                builder.addPropertyValue("preSend", false);
            }

            if (StringUtils.hasText(postSendFlag)) {
                builder.addPropertyValue("postSend", Boolean.parseBoolean(postSendFlag));
            } else {
                builder.addPropertyValue("postSend", false);
            }

            if (StringUtils.hasText(preReceiveFlag)) {
                builder.addPropertyValue("preReceive", Boolean.parseBoolean(preReceiveFlag));
            } else {
                builder.addPropertyValue("preReceive", false);
            }

            if (StringUtils.hasText(postReceiveFlag)) {
                builder.addPropertyValue("postReceive", Boolean.parseBoolean(postReceiveFlag));
            } else {
                builder.addPropertyValue("postReceive", false);
            }
        }
    }

}
