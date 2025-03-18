/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.png;

public enum InterlaceMethod
{
    NONE(false), 
    ADAM7(true);
    
    private final boolean progressive;
    
    private InterlaceMethod(final boolean progressive) {
        this.progressive = progressive;
    }
    
    public boolean isProgressive() {
        return this.progressive;
    }
}
