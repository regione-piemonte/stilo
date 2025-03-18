/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

public interface DomExtension
{
    float getPriority();
    
    String getAuthor();
    
    String getContactAddress();
    
    String getURL();
    
    String getDescription();
    
    void registerTags(final ExtensibleDOMImplementation p0);
}
