// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgbrowser;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class TransformHistory
{
    protected List transforms;
    protected int position;
    
    public TransformHistory() {
        this.transforms = new ArrayList();
        this.position = -1;
    }
    
    public void back() {
        this.position -= 2;
    }
    
    public boolean canGoBack() {
        return this.position > 0;
    }
    
    public void forward() {
    }
    
    public boolean canGoForward() {
        return this.position < this.transforms.size() - 1;
    }
    
    public AffineTransform currentTransform() {
        return this.transforms.get(this.position + 1);
    }
    
    public void update(final AffineTransform obj) {
        if (this.position < -1) {
            this.position = -1;
        }
        if (++this.position < this.transforms.size()) {
            if (!this.transforms.get(this.position).equals(obj)) {
                this.transforms = this.transforms.subList(0, this.position + 1);
            }
            this.transforms.set(this.position, obj);
        }
        else {
            this.transforms.add(obj);
        }
    }
}
