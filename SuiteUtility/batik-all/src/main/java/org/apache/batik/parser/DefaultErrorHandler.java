// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

public class DefaultErrorHandler implements ErrorHandler
{
    public void error(final ParseException ex) throws ParseException {
        throw ex;
    }
}
