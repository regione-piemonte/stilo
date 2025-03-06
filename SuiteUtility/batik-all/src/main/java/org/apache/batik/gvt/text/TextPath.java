// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.text;

import java.awt.geom.Point2D;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import org.apache.batik.ext.awt.geom.PathLength;

public class TextPath
{
    private PathLength pathLength;
    private float startOffset;
    
    public TextPath(final GeneralPath generalPath) {
        this.pathLength = new PathLength(generalPath);
        this.startOffset = 0.0f;
    }
    
    public void setStartOffset(final float startOffset) {
        this.startOffset = startOffset;
    }
    
    public float getStartOffset() {
        return this.startOffset;
    }
    
    public float lengthOfPath() {
        return this.pathLength.lengthOfPath();
    }
    
    public float angleAtLength(final float n) {
        return this.pathLength.angleAtLength(n);
    }
    
    public Point2D pointAtLength(final float n) {
        return this.pathLength.pointAtLength(n);
    }
}
