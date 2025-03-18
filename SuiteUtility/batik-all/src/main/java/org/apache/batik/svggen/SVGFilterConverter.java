/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.util.List;
import java.awt.Rectangle;
import java.awt.image.BufferedImageOp;

public interface SVGFilterConverter extends SVGSyntax
{
    SVGFilterDescriptor toSVG(final BufferedImageOp p0, final Rectangle p1);
    
    List getDefinitionSet();
}
