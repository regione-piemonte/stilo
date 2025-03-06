// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.icc;

import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;

interface IccTagDataType
{
    String getName();
    
    int getSignature();
    
    void dump(final String p0, final byte[] p1) throws ImageReadException, IOException;
}
