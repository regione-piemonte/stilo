// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.util.ParsedURL;

public class DefaultScriptSecurity implements ScriptSecurity
{
    public static final String DATA_PROTOCOL = "data";
    public static final String ERROR_CANNOT_ACCESS_DOCUMENT_URL = "DefaultScriptSecurity.error.cannot.access.document.url";
    public static final String ERROR_SCRIPT_FROM_DIFFERENT_URL = "DefaultScriptSecurity.error.script.from.different.url";
    protected SecurityException se;
    
    public void checkLoadScript() {
        if (this.se != null) {
            throw this.se;
        }
    }
    
    public DefaultScriptSecurity(final String s, final ParsedURL parsedURL, final ParsedURL parsedURL2) {
        if (parsedURL2 == null) {
            this.se = new SecurityException(Messages.formatMessage("DefaultScriptSecurity.error.cannot.access.document.url", new Object[] { parsedURL }));
        }
        else {
            final String host = parsedURL2.getHost();
            final String host2 = parsedURL.getHost();
            if (host != host2 && (host == null || !host.equals(host2)) && !parsedURL2.equals(parsedURL) && (parsedURL == null || !"data".equals(parsedURL.getProtocol()))) {
                this.se = new SecurityException(Messages.formatMessage("DefaultScriptSecurity.error.script.from.different.url", new Object[] { parsedURL }));
            }
        }
    }
}
