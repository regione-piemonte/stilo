/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgbrowser;

import org.w3c.dom.Element;

public interface NodePickerController
{
    boolean isEditable();
    
    boolean canEdit(final Element p0);
}
