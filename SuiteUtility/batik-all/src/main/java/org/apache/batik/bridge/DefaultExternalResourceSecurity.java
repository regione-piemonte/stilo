// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.util.ParsedURL;

public class DefaultExternalResourceSecurity implements ExternalResourceSecurity
{
    public static final String DATA_PROTOCOL = "data";
    public static final String ERROR_CANNOT_ACCESS_DOCUMENT_URL = "DefaultExternalResourceSecurity.error.cannot.access.document.url";
    public static final String ERROR_EXTERNAL_RESOURCE_FROM_DIFFERENT_URL = "DefaultExternalResourceSecurity.error.external.resource.from.different.url";
    protected SecurityException se;
    
    public void checkLoadExternalResource() {
        if (this.se != null) {
            this.se.fillInStackTrace();
            throw this.se;
        }
    }
    
    public DefaultExternalResourceSecurity(final ParsedURL parsedURL, final ParsedURL parsedURL2) {
        if (parsedURL2 == null) {
            this.se = new SecurityException(Messages.formatMessage("DefaultExternalResourceSecurity.error.cannot.access.document.url", new Object[] { parsedURL }));
        }
        else {
            final String host = parsedURL2.getHost();
            final String host2 = parsedURL.getHost();
            if (host != host2 && (host == null || !host.equals(host2)) && (parsedURL == null || !"data".equals(parsedURL.getProtocol()))) {
                this.se = new SecurityException(Messages.formatMessage("DefaultExternalResourceSecurity.error.external.resource.from.different.url", new Object[] { parsedURL }));
            }
        }
    }
}
