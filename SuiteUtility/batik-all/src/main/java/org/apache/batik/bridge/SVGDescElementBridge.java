// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

public class SVGDescElementBridge extends SVGDescriptiveElementBridge
{
    public String getLocalName() {
        return "desc";
    }
    
    public Bridge getInstance() {
        return new SVGDescElementBridge();
    }
}
