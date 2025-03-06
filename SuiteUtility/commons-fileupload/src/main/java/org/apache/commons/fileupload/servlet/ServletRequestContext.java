// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.fileupload.servlet;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.RequestContext;

public class ServletRequestContext implements RequestContext
{
    private HttpServletRequest request;
    
    public ServletRequestContext(final HttpServletRequest request) {
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
        return (InputStream)this.request.getInputStream();
    }
    
    @Override
    public String toString() {
        return String.format("ContentLength=%s, ContentType=%s", this.getContentLength(), this.getContentType());
    }
}
