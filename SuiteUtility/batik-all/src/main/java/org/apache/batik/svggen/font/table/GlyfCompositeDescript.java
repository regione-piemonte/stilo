// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.util.Iterator;
import java.util.ArrayList;
import java.io.ByteArrayInputStream;
import java.util.List;

public class GlyfCompositeDescript extends GlyfDescript
{
    private List components;
    protected boolean beingResolved;
    protected boolean resolved;
    
    public GlyfCompositeDescript(final GlyfTable glyfTable, final ByteArrayInputStream byteArrayInputStream) {
        super(glyfTable, (short)(-1), byteArrayInputStream);
        this.components = new ArrayList();
        this.beingResolved = false;
        this.resolved = false;
        GlyfCompositeComp glyfCompositeComp;
        do {
            glyfCompositeComp = new GlyfCompositeComp(byteArrayInputStream);
            this.components.add(glyfCompositeComp);
        } while ((glyfCompositeComp.getFlags() & 0x20) != 0x0);
        if ((glyfCompositeComp.getFlags() & 0x100) != 0x0) {
            this.readInstructions(byteArrayInputStream, byteArrayInputStream.read() << 8 | byteArrayInputStream.read());
        }
    }
    
    public void resolve() {
        if (this.resolved) {
            return;
        }
        if (this.beingResolved) {
            System.err.println("Circular reference in GlyfCompositeDesc");
            return;
        }
        this.beingResolved = true;
        int firstIndex = 0;
        int firstContour = 0;
        for (final GlyfCompositeComp glyfCompositeComp : this.components) {
            glyfCompositeComp.setFirstIndex(firstIndex);
            glyfCompositeComp.setFirstContour(firstContour);
            final GlyfDescript description = this.parentTable.getDescription(glyfCompositeComp.getGlyphIndex());
            if (description != null) {
                description.resolve();
                firstIndex += description.getPointCount();
                firstContour += description.getContourCount();
            }
        }
        this.resolved = true;
        this.beingResolved = false;
    }
    
    public int getEndPtOfContours(final int n) {
        final GlyfCompositeComp compositeCompEndPt = this.getCompositeCompEndPt(n);
        if (compositeCompEndPt != null) {
            return this.parentTable.getDescription(compositeCompEndPt.getGlyphIndex()).getEndPtOfContours(n - compositeCompEndPt.getFirstContour()) + compositeCompEndPt.getFirstIndex();
        }
        return 0;
    }
    
    public byte getFlags(final int n) {
        final GlyfCompositeComp compositeComp = this.getCompositeComp(n);
        if (compositeComp != null) {
            return this.parentTable.getDescription(compositeComp.getGlyphIndex()).getFlags(n - compositeComp.getFirstIndex());
        }
        return 0;
    }
    
    public short getXCoordinate(final int n) {
        final GlyfCompositeComp compositeComp = this.getCompositeComp(n);
        if (compositeComp != null) {
            final GlyfDescript description = this.parentTable.getDescription(compositeComp.getGlyphIndex());
            final int n2 = n - compositeComp.getFirstIndex();
            return (short)((short)compositeComp.scaleX(description.getXCoordinate(n2), description.getYCoordinate(n2)) + compositeComp.getXTranslate());
        }
        return 0;
    }
    
    public short getYCoordinate(final int n) {
        final GlyfCompositeComp compositeComp = this.getCompositeComp(n);
        if (compositeComp != null) {
            final GlyfDescript description = this.parentTable.getDescription(compositeComp.getGlyphIndex());
            final int n2 = n - compositeComp.getFirstIndex();
            return (short)((short)compositeComp.scaleY(description.getXCoordinate(n2), description.getYCoordinate(n2)) + compositeComp.getYTranslate());
        }
        return 0;
    }
    
    public boolean isComposite() {
        return true;
    }
    
    public int getPointCount() {
        if (!this.resolved) {
            System.err.println("getPointCount called on unresolved GlyfCompositeDescript");
        }
        final GlyfCompositeComp glyfCompositeComp = this.components.get(this.components.size() - 1);
        return glyfCompositeComp.getFirstIndex() + this.parentTable.getDescription(glyfCompositeComp.getGlyphIndex()).getPointCount();
    }
    
    public int getContourCount() {
        if (!this.resolved) {
            System.err.println("getContourCount called on unresolved GlyfCompositeDescript");
        }
        final GlyfCompositeComp glyfCompositeComp = this.components.get(this.components.size() - 1);
        return glyfCompositeComp.getFirstContour() + this.parentTable.getDescription(glyfCompositeComp.getGlyphIndex()).getContourCount();
    }
    
    public int getComponentIndex(final int n) {
        return this.components.get(n).getFirstIndex();
    }
    
    public int getComponentCount() {
        return this.components.size();
    }
    
    protected GlyfCompositeComp getCompositeComp(final int n) {
        for (int i = 0; i < this.components.size(); ++i) {
            final GlyfCompositeComp glyfCompositeComp = this.components.get(i);
            final GlyfDescript description = this.parentTable.getDescription(glyfCompositeComp.getGlyphIndex());
            if (glyfCompositeComp.getFirstIndex() <= n && n < glyfCompositeComp.getFirstIndex() + description.getPointCount()) {
                return glyfCompositeComp;
            }
        }
        return null;
    }
    
    protected GlyfCompositeComp getCompositeCompEndPt(final int n) {
        for (int i = 0; i < this.components.size(); ++i) {
            final GlyfCompositeComp glyfCompositeComp = this.components.get(i);
            final GlyfDescript description = this.parentTable.getDescription(glyfCompositeComp.getGlyphIndex());
            if (glyfCompositeComp.getFirstContour() <= n && n < glyfCompositeComp.getFirstContour() + description.getContourCount()) {
                return glyfCompositeComp;
            }
        }
        return null;
    }
}
