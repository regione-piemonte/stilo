// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.fileupload;

import java.io.IOException;

public interface FileItemIterator
{
    boolean hasNext() throws FileUploadException, IOException;
    
    FileItemStream next() throws FileUploadException, IOException;
}
