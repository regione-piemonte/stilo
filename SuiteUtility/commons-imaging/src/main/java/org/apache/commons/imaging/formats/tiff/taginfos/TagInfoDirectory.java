/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.taginfos;

import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType;

public class TagInfoDirectory extends TagInfoLong
{
    public TagInfoDirectory(final String name, final int tag, final TiffDirectoryType directoryType) {
        super(name, tag, directoryType, true);
    }
}
