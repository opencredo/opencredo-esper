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

package org.opencredo.esper.samples.sheep;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.event.map.MapEventBean;
import org.opencredo.esper.EsperStatement;
import org.opencredo.esper.EsperTemplate;

/**
 * Simple example showing use of EsperTemplate to count sheep from a passing
 * menagerie.
 * 
 * @author Jonas Partner (jonas.partner@opencredo.com)
 */
public class Insomnia {

    public static void main(String[] ars) {
        EsperStatement statement = new EsperStatement("insert into Sleep select count(*) as total from org.opencredo.esper.samples.sheep.Animal(type='sheep').win:time(10 sec) having count(*) > 100");

        EsperStatement sleepStatment = new EsperStatement("select * from Sleep");
        sleepStatment.addListener(new LoggingListener());

        EsperTemplate template = new EsperTemplate();
        template.addStatement(statement);
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
