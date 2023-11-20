/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.wso2.carbon.identity.sso.agent.openid;

public interface AttributesRequestor
{
    void init();
    
    String[] getRequestedAttributes(final String p0);
    
    boolean isRequired(final String p0, final String p1);
    
    String getTypeURI(final String p0, final String p1);
    
    int getCount(final String p0, final String p1);
}
