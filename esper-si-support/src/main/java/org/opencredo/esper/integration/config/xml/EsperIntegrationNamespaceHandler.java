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

import org.springframework.beans.factory.xml.NamespaceHandler;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * 
 * @author Russ Miles (russell.miles@opencredo.com)
 * 
 */
public class EsperIntegrationNamespaceHandler extends NamespaceHandlerSupport implements NamespaceHandler {

    public void init() {
        registerBeanDefinitionParser("inbound-channel-adapter", new InboundChannelAdapterParser());
        registerBeanDefinitionParser("wire-tap", new EsperWireTapParser());
        registerBeanDefinitionParser("wire-tap-channels", new EsperWireTapChannelsParser());
        registerBeanDefinitionParser("channel-throughput-monitor", new EsperChannelThroughputMonitorParser());

    }
}
