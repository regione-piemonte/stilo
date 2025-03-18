/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.ext.awt.image.renderable.Filter;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;

public interface FilterBridge extends Bridge
{
    Filter createFilter(final BridgeContext p0, final Element p1, final Element p2, final GraphicsNode p3);
}
