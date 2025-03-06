// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.util.SVGConstants;

public abstract class AbstractSVGBridge implements Bridge, SVGConstants
{
    protected AbstractSVGBridge() {
    }
    
    public String getNamespaceURI() {
        return "http://www.w3.org/2000/svg";
    }
    
    public Bridge getInstance() {
        return this;
    }
}
