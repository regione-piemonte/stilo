/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

public class SVGDescElementBridge extends SVGDescriptiveElementBridge
{
    public String getLocalName() {
        return "desc";
    }
    
    public Bridge getInstance() {
        return new SVGDescElementBridge();
    }
}
