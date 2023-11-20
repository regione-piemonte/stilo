/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.pdfbox.jbig2.util.log;

import java.util.Iterator;

import org.apache.pdfbox.jbig2.util.ServiceLookup;

/**
 * Retrieves a {@link Logger} via registered {@link LoggerBridge} through META-INF/services lookup.
 */
public class LoggerFactory
{

    private static LoggerBridge loggerBridge;

    private static ClassLoader clsLoader;

    public static Logger getLogger(Class<?> cls, ClassLoader clsLoader)
    {
        if (null == loggerBridge)
        {
            final ServiceLookup<LoggerBridge> serviceLookup = new ServiceLookup<LoggerBridge>();
            final Iterator<LoggerBridge> loggerBridgeServices = serviceLookup
                    .getServices(LoggerBridge.class, clsLoader);

            if (!loggerBridgeServices.hasNext())
            {
                throw new IllegalStateException("No implementation of " + LoggerBridge.class
                        + " was avaliable using META-INF/services lookup");
            }
            loggerBridge = loggerBridgeServices.next();
        }
        return loggerBridge.getLogger(cls);
    }

    public static Logger getLogger(Class<?> cls)
    {
        //return getLogger(cls, clsLoader != null ? clsLoader : LoggerBridge.class.getClassLoader());
    	/**
    	 * Viene istanziato direttamente un oggetto di tipo JDKLogger anzichè andare a recuperare l'istanza tramite classloader
    	 * Problema: java.util.ServiceConfigurationError: org.apache.pdfbox.jbig2.util.log.LoggerBridge: Provider org.apache.pdfbox.jbig2.util.log.JDKLoggerBridge not a subtype
    	 */
    	
    	return new JDKLogger(java.util.logging.Logger.getLogger(cls.getName()));
    }

    public static void setClassLoader(ClassLoader clsLoader)
    {
        LoggerFactory.clsLoader = clsLoader;
    }

}
