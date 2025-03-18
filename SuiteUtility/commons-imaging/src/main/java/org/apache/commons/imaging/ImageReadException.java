/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging;

public class ImageReadException extends ImagingException
{
    private static final long serialVersionUID = -1L;
    
    public ImageReadException(final String message) {
        super(message);
    }
    
    public ImageReadException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
