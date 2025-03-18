/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.common.itu_t4;

class HuffmanTreeException extends Exception
{
    private static final long serialVersionUID = 1L;
    
    HuffmanTreeException(final String message) {
        super(message);
    }
    
    HuffmanTreeException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
