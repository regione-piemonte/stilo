// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.transcoder.keys;

import org.w3c.dom.DOMImplementation;
import org.apache.batik.transcoder.TranscodingHints;

public class DOMImplementationKey extends TranscodingHints.Key
{
    public boolean isCompatibleValue(final Object o) {
        return o instanceof DOMImplementation;
    }
}
