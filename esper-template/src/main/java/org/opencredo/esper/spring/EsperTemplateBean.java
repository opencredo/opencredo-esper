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

package org.opencredo.esper.spring;

import org.opencredo.esper.EsperTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Provides some convenient extensions to {@link EsperTemplate} when configuring
 * the template within a Spring environment.
 * 
 * @author Russ Miles (russ.miles@opencredo.com)
 * 
 */
public class EsperTemplateBean extends EsperTemplate implements BeanNameAware, InitializingBean, DisposableBean {
    private final static Logger LOG = LoggerFactory.getLogger(EsperTemplateBean.class);

    public void setBeanName(String name) {
        super.setName(name);
        LOG.debug("Set esper template bean name to " + name);
    }

    public void afterPropertiesSet() {
        LOG.debug("Initializing the esper template bean");
        super.initialize();
        LOG.debug("Completed initializing the esper template bean");
    }

    public void destroy() {
        LOG.debug("Destroying the esper template bean");
        super.cleanup();
        LOG.debug("Finished destroying the esper template bean");
    }
}
