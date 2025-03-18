/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.filter;

import org.apache.batik.gvt.GraphicsNode;

public class ConcreteGraphicsNodeRableFactory implements GraphicsNodeRableFactory
{
    public GraphicsNodeRable createGraphicsNodeRable(final GraphicsNode graphicsNode) {
        return (GraphicsNodeRable)graphicsNode.getGraphicsNodeRable(true);
    }
}
