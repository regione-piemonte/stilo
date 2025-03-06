// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.util.ParsedURL;

public class EmbededScriptSecurity implements ScriptSecurity
{
    public static final String DATA_PROTOCOL = "data";
    public static final String ERROR_CANNOT_ACCESS_DOCUMENT_URL = "DefaultScriptSecurity.error.cannot.access.document.url";
    public static final String ERROR_SCRIPT_NOT_EMBEDED = "EmbededScriptSecurity.error.script.not.embeded";
    protected SecurityException se;
    
    public void checkLoadScript() {
        if (this.se != null) {
            throw this.se;
        }
    }
    
    public EmbededScriptSecurity(final String s, final ParsedURL parsedURL, final ParsedURL parsedURL2) {
        if (parsedURL2 == null) {
            this.se = new SecurityException(Messages.formatMessage("DefaultScriptSecurity.error.cannot.access.document.url", new Object[] { parsedURL }));
        }
        else if (!parsedURL2.equals(parsedURL) && (parsedURL == null || !"data".equals(parsedURL.getProtocol()))) {
            this.se = new SecurityException(Messages.formatMessage("EmbededScriptSecurity.error.script.not.embeded", new Object[] { parsedURL }));
        }
    }
}
