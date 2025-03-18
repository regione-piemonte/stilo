/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.w3c.dom;

public interface ElementTraversal
{
    Element getFirstElementChild();
    
    Element getLastElementChild();
    
    Element getNextElementSibling();
    
    Element getPreviousElementSibling();
    
    int getChildElementCount();
}
