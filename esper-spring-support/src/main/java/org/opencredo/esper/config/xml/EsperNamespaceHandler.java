package org.opencredo.esper.config.xml;

import org.springframework.beans.factory.xml.NamespaceHandler;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class EsperNamespaceHandler extends NamespaceHandlerSupport implements NamespaceHandler {

	public void init() {
		registerBeanDefinitionParser("esper-engine", new EsperEngineParser());
	}

}
