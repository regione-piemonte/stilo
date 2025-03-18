/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import java.awt.geom.AffineTransform;

public class SVGOMMatrix extends AbstractSVGMatrix
{
    protected AffineTransform affineTransform;
    
    public SVGOMMatrix(final AffineTransform affineTransform) {
        this.affineTransform = affineTransform;
    }
    
    protected AffineTransform getAffineTransform() {
        return this.affineTransform;
    }
}
