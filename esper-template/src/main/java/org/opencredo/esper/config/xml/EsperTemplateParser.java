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

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedSet;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

/**
 * Parses out an esper-template element.
 * 
 * @author Russ Miles (russ.miles@opencredo.com)
 *
 */
public class EsperTemplateParser extends AbstractBeanDefinitionParser {

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
				+ ".spring.EsperTemplateBean");

		initialiseStatements(element, parserContext, builder);
		
		initializeConfiguration(element, builder);
		
		initializeUnmatchedListener(element, builder);
		
		return builder.getBeanDefinition();
	}

	private void initializeUnmatchedListener(Element element,
			BeanDefinitionBuilder builder) {
		String unmatchedListenerRef = (String) element.getAttribute(EsperNamespaceUtils.UNMATCHED_LISTENER_ATTRIBUTE);
		
		if (StringUtils.hasText(unmatchedListenerRef)) {
			builder.addPropertyReference("unmatchedListener", unmatchedListenerRef);
		}
	}

	private void initializeConfiguration(Element element,
			BeanDefinitionBuilder builder) {
		String configuration = (String) element.getAttribute(EsperNamespaceUtils.CONFIGURATION_ATTRIBUTE);
		
		if (StringUtils.hasText(configuration)) {
			builder.addPropertyValue("configuration", configuration);
		}
	}

	private void initialiseStatements(Element element,
			ParserContext parserContext, BeanDefinitionBuilder builder) {
		ManagedSet statements = null;
		
		Element statementsElement = DomUtils.getChildElementByTagName(element, "statements");
		if (statementsElement != null) {
			EsperStatementParser statementParser = new EsperStatementParser();
			statements = statementParser.parseStatements(statementsElement, parserContext);
		}
		
		if (statements != null) {
			builder.addPropertyValue("statements", statements);
		}
	}
}
