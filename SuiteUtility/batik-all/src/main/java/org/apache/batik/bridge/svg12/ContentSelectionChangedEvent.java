/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge.svg12;

import org.apache.batik.dom.svg12.XBLOMContentElement;
import java.util.EventObject;

public class ContentSelectionChangedEvent extends EventObject
{
    public ContentSelectionChangedEvent(final XBLOMContentElement source) {
        super(source);
    }
    
    public XBLOMContentElement getContentElement() {
        return (XBLOMContentElement)this.source;
    }
}
