/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.svg12;

import org.apache.batik.css.engine.value.FloatValue;

public class LineHeightValue extends FloatValue
{
    protected boolean fontSizeRelative;
    
    public LineHeightValue(final short n, final float n2, final boolean fontSizeRelative) {
        super(n, n2);
        this.fontSizeRelative = fontSizeRelative;
    }
    
    public boolean getFontSizeRelative() {
        return this.fontSizeRelative;
    }
}
