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
package org.opencredo.esper.samples.sheep;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.event.map.MapEventBean;
import org.opencredo.esper.EsperStatement;
import org.opencredo.esper.EsperTemplate;

/**
 * Simple example showing use of EsperTemplate to count sheep from a passing 
 * @author Jonas Partner
 *
 */
public class Insomnia {

    public static void main(String[] ars){
        EsperTemplate template = new EsperTemplate();


             EsperStatement statement = new EsperStatement("insert into Sleep select count(*) as total from org.opencredo.esper.samples.sheep.Animal(type='sheep').win:time(10 sec) having count(*) > 100");
             template.addStatement(statement);

             EsperStatement sleepStatment = new EsperStatement("select * from Sleep");
             sleepStatment.addListener(new LoggingListener());
             template.addStatement(sleepStatment);
             template.initialize();


             for (int i = 0; i < 109; i++) {
                 template.sendEvent(new Animal("cat"));
                 template.sendEvent(new Animal("sheep"));

             }

    }

    private static class LoggingListener implements UpdateListener {
        public void update(EventBean[] newEvents, EventBean[] oldEvents) {
            if (newEvents != null) {
                for (EventBean newEvent : newEvents) {
                    MapEventBean bean = (MapEventBean) newEvent;
                    System.out.println("Trying to sleep after " + bean.get("total") + " sheep");
                }
            }

        }
    }
}
