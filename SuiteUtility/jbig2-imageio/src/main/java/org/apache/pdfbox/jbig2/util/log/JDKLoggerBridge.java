/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.util.log;

public class JDKLoggerBridge implements LoggerBridge
{
    @Override
    public Logger getLogger(final Class<?> clazz) {
        return new JDKLogger(java.util.logging.Logger.getLogger(clazz.getName()));
    }
}
