/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.text;

import org.apache.batik.gvt.TextNode;

public interface Mark
{
    TextNode getTextNode();
    
    int getCharIndex();
}
