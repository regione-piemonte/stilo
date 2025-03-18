/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.common;

import java.util.List;

public interface ImageMetadata
{
    String toString(final String p0);
    
    List<? extends ImageMetadataItem> getItems();
    
    public interface ImageMetadataItem
    {
        String toString(final String p0);
        
        String toString();
    }
}
