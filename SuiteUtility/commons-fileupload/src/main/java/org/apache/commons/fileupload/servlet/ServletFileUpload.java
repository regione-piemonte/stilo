// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.fileupload.servlet;

import java.io.IOException;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.FileItem;
import java.util.List;
import org.apache.commons.fileupload.FileItemFactory;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileUpload;

public class ServletFileUpload extends FileUpload
{
    public static final boolean isMultipartContent(final HttpServletRequest request) {
        if (!"post".equals(request.getMethod().toLowerCase())) {
            return false;
        }
        final String contentType = request.getContentType();
        return contentType != null && contentType.toLowerCase().startsWith("multipart/");
    }
    
    public ServletFileUpload() {
    }
    
    public ServletFileUpload(final FileItemFactory fileItemFactory) {
        super(fileItemFactory);
    }
    
    @Override
    public List<FileItem> parseRequest(final HttpServletRequest request) throws FileUploadException {
        return this.parseRequest(new ServletRequestContext(request));
    }
    
    public FileItemIterator getItemIterator(final HttpServletRequest request) throws FileUploadException, IOException {
        return super.getItemIterator(new ServletRequestContext(request));
    }
}
