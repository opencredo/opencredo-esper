package org.opencredo.esper.integration.config.xml;

import org.springframework.beans.factory.xml.NamespaceHandler;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * 
 * @author Russ Miles (russell.miles@opencredo.com)
 *
 */
public class EsperIntegrationNamespaceHandler extends NamespaceHandlerSupport implements NamespaceHandler {

	public void init() {
		registerBeanDefinitionParser("inbound-gateway", new InboundGatewayParser());
	}
}
