/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.fileupload;

import java.io.IOException;
import java.io.InputStream;

public interface RequestContext
{
    String getCharacterEncoding();
    
    String getContentType();
    
    int getContentLength();
    
    InputStream getInputStream() throws IOException;
}
