// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.util.ParsedURL;

public class EmbededExternalResourceSecurity implements ExternalResourceSecurity
{
    public static final String DATA_PROTOCOL = "data";
    public static final String ERROR_EXTERNAL_RESOURCE_NOT_EMBEDED = "EmbededExternalResourceSecurity.error.external.esource.not.embeded";
    protected SecurityException se;
    
    public void checkLoadExternalResource() {
        if (this.se != null) {
            throw this.se;
        }
    }
    
    public EmbededExternalResourceSecurity(final ParsedURL parsedURL) {
        if (parsedURL == null || !"data".equals(parsedURL.getProtocol())) {
            this.se = new SecurityException(Messages.formatMessage("EmbededExternalResourceSecurity.error.external.esource.not.embeded", new Object[] { parsedURL }));
        }
    }
}
