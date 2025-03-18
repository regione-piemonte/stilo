/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

import java.awt.Shape;

public interface ShapeProducer
{
    Shape getShape();
    
    void setWindingRule(final int p0);
    
    int getWindingRule();
}
