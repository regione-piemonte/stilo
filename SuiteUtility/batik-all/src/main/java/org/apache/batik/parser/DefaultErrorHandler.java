/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

public class DefaultErrorHandler implements ErrorHandler
{
    public void error(final ParseException ex) throws ParseException {
        throw ex;
    }
}
