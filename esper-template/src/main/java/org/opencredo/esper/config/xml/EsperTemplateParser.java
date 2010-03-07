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
