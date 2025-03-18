/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.common;

import java.awt.image.BufferedImage;

public interface BufferedImageFactory
{
    BufferedImage getColorBufferedImage(final int p0, final int p1, final boolean p2);
    
    BufferedImage getGrayscaleBufferedImage(final int p0, final int p1, final boolean p2);
}
