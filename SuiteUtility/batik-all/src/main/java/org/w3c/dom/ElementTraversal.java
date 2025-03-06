// 
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
