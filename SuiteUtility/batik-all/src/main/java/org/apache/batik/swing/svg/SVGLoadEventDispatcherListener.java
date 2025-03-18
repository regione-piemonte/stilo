/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.swing.svg;

public interface SVGLoadEventDispatcherListener
{
    void svgLoadEventDispatchStarted(final SVGLoadEventDispatcherEvent p0);
    
    void svgLoadEventDispatchCompleted(final SVGLoadEventDispatcherEvent p0);
    
    void svgLoadEventDispatchCancelled(final SVGLoadEventDispatcherEvent p0);
    
    void svgLoadEventDispatchFailed(final SVGLoadEventDispatcherEvent p0);
}
