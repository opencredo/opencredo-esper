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

package org.opencredo.esper.sample;

import static org.opencredo.esper.sample.Status.COMPLETE;
import static org.opencredo.esper.sample.Status.FAILED;
import static org.opencredo.esper.sample.Status.IN_PROGRESS;
import static org.opencredo.esper.sample.Status.RECEIVED;

public class DefaultSampleService {

    private long sleepDuration;

    public void setSleepDuration(long sleepDuration) {
        this.sleepDuration = sleepDuration;
    }

    public void actionEvent(SampleEvent event) {
        event.setStatus(RECEIVED);

        event.setStatus(IN_PROGRESS);

        try {
            Thread.sleep(this.sleepDuration);
        } catch (InterruptedException e) {
            e.printStackTrace();

            event.setStatus(FAILED);
        }

        event.setStatus(COMPLETE);
    }

    public SampleEvent actionEventWithResponse(SampleEvent event) {
        event.setStatus(RECEIVED);

        event.setStatus(IN_PROGRESS);

        try {
            Thread.sleep(this.sleepDuration);
        } catch (InterruptedException e) {
            e.printStackTrace();

            event.setStatus(FAILED);
        }

        event.setStatus(COMPLETE);
        return event;
    }
}
