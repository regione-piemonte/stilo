/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.fileupload;

import java.util.Iterator;

public interface FileItemHeaders
{
    String getHeader(final String p0);
    
    Iterator<String> getHeaders(final String p0);
    
    Iterator<String> getHeaderNames();
}
