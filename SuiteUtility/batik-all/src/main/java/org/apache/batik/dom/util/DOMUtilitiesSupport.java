// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.util;

public abstract class DOMUtilitiesSupport
{
    static final String[] BITS;
    
    protected static String getModifiersList(final int n, int n2) {
        if ((n2 & 0x2000) != 0x0) {
            n2 = (0x10 | (n2 >> 6 & 0xF));
        }
        else {
            n2 = (n2 >> 6 & 0xF);
        }
        final String str = DOMUtilities.LOCK_STRINGS[n & 0xF];
        if (str.length() != 0) {
            return str + ' ' + DOMUtilities.MODIFIER_STRINGS[n2];
        }
        return DOMUtilities.MODIFIER_STRINGS[n2];
    }
    
    static {
        BITS = new String[] { "Shift", "Ctrl", "Meta-or-Button3", "Alt-or-Button2", "Button1", "AltGraph", "ShiftDown", "CtrlDown", "MetaDown", "AltDown", "Button1Down", "Button2Down", "Button3Down", "AltGraphDown" };
    }
}
