/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge.svg12;

import org.w3c.dom.Element;
import java.util.EventListener;

public interface BindingListener extends EventListener
{
    void bindingChanged(final Element p0, final Element p1);
}
