/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.png.chunks;

public class PngChunkIdat extends PngChunk
{
    public PngChunkIdat(final int length, final int chunkType, final int crc, final byte[] bytes) {
        super(length, chunkType, crc, bytes);
    }
}
