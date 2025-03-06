// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.util;

public enum CombinationOperator
{
    OR, 
    AND, 
    XOR, 
    XNOR, 
    REPLACE;
    
    public static CombinationOperator translateOperatorCodeToEnum(final short n) {
        switch (n) {
            case 0: {
                return CombinationOperator.OR;
            }
            case 1: {
                return CombinationOperator.AND;
            }
            case 2: {
                return CombinationOperator.XOR;
            }
            case 3: {
                return CombinationOperator.XNOR;
            }
            default: {
                return CombinationOperator.REPLACE;
            }
        }
    }
}
