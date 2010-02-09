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

package org.opencredo.esper;

import com.espertech.esper.client.EPStatement;

/**
 * Provides a callback hook in order to expose the underlying
 * Esper resources, such as the {@link EPStatement}.
 * 
 * @author Russ Miles (russ.miles@opencredo.com)
 */
public interface NativeEPStatementCallback {
	/**
	 * Passes the native EPStatement and registered EPL filter query
	 * to the implementor.
	 * 
	 * @param nativeStatement the native EPStatement
	 * @param epl the epl filter query registered with this statement
	 */
	public void executeWithEPStatement(EPStatement nativeStatement, String epl);
}
