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
		registerBeanDefinitionParser("wire-tap", new EsperWireTapParser());
		registerBeanDefinitionParser("wire-tap-channels", new EsperWireTapChannelsParser());
	}
}
