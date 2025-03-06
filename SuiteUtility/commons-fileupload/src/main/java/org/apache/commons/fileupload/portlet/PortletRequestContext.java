// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.fileupload.portlet;

import java.io.IOException;
import java.io.InputStream;
import javax.portlet.ActionRequest;
import org.apache.commons.fileupload.RequestContext;

public class PortletRequestContext implements RequestContext
{
    private ActionRequest request;
    
    public PortletRequestContext(final ActionRequest request) {
        this.request = request;
    }
    
    public String getCharacterEncoding() {
        return this.request.getCharacterEncoding();
    }
    
    public String getContentType() {
        return this.request.getContentType();
    }
    
    public int getContentLength() {
        return this.request.getContentLength();
    }
    
    public InputStream getInputStream() throws IOException {
        return this.request.getPortletInputStream();
    }
    
    @Override
    public String toString() {
        return String.format("ContentLength=%s, ContentType=%s", this.getContentLength(), this.getContentType());
    }
}
