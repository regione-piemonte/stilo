/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.fileupload;

public interface ProgressListener
{
    void update(final long p0, final long p1, final int p2);
}
