/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.palette;

import org.apache.commons.imaging.ImageWriteException;
import java.util.List;

public interface MedianCut
{
    boolean performNextMedianCut(final List<ColorGroup> p0, final boolean p1) throws ImageWriteException;
}
