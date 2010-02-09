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

package org.opencredo.esper.spring;

import org.opencredo.esper.EsperTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Provides some convenient extensions to {@link EsperTemplate} when
 * configuring the template within a Spring environment.
 * 
 * @author Russ Miles (russ.miles@opencredo.com)
 * 
 */
public class EsperTemplateBean extends EsperTemplate implements BeanNameAware,
		InitializingBean, DisposableBean {
	private final static Logger LOG = LoggerFactory.getLogger(EsperTemplateBean.class);
	
	public void setBeanName(String name) {
		super.setName(name);
		LOG.debug("Set esper template bean name to " + name);
	}
	
	public void afterPropertiesSet() throws Exception {
		LOG.debug("Initializing the esper template bean");
		super.initialize();
		LOG.debug("Completed initializing the esper template bean");
	}

	public void destroy() throws Exception {
		LOG.debug("Destroying the esper template bean");
		super.cleanup();
		LOG.debug("Finished destroying the esper template bean");
	}
}
