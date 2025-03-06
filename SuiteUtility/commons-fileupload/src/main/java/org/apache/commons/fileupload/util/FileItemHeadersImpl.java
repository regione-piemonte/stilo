// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.fileupload.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.io.Serializable;
import org.apache.commons.fileupload.FileItemHeaders;

public class FileItemHeadersImpl implements FileItemHeaders, Serializable
{
    private static final long serialVersionUID = -4455695752627032559L;
    private final Map<String, List<String>> headerNameToValueListMap;
    
    public FileItemHeadersImpl() {
        this.headerNameToValueListMap = new LinkedHashMap<String, List<String>>();
    }
    
    public String getHeader(final String name) {
        final String nameLower = name.toLowerCase();
        final List<String> headerValueList = this.headerNameToValueListMap.get(nameLower);
        if (null == headerValueList) {
            return null;
        }
        return headerValueList.get(0);
    }
    
    public Iterator<String> getHeaderNames() {
        return this.headerNameToValueListMap.keySet().iterator();
    }
    
    public Iterator<String> getHeaders(final String name) {
        final String nameLower = name.toLowerCase();
        List<String> headerValueList = this.headerNameToValueListMap.get(nameLower);
        if (null == headerValueList) {
            headerValueList = Collections.emptyList();
        }
        return headerValueList.iterator();
    }
    
    public synchronized void addHeader(final String name, final String value) {
        final String nameLower = name.toLowerCase();
        List<String> headerValueList = this.headerNameToValueListMap.get(nameLower);
        if (null == headerValueList) {
            headerValueList = new ArrayList<String>();
            this.headerNameToValueListMap.put(nameLower, headerValueList);
        }
        headerValueList.add(value);
    }
}
