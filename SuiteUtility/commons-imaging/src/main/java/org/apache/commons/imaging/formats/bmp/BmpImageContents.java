/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.bmp;

class BmpImageContents
{
    final BmpHeaderInfo bhi;
    final byte[] colorTable;
    final byte[] imageData;
    final PixelParser pixelParser;
    
    BmpImageContents(final BmpHeaderInfo bhi, final byte[] colorTable, final byte[] imageData, final PixelParser pixelParser) {
        this.bhi = bhi;
        this.colorTable = colorTable;
        this.imageData = imageData;
        this.pixelParser = pixelParser;
    }
}
