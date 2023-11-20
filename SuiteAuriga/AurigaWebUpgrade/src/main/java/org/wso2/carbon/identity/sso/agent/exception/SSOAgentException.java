/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.wso2.carbon.identity.sso.agent.exception;

import javax.servlet.ServletException;

public class SSOAgentException extends ServletException
{
    public SSOAgentException(final String message) {
        super(message);
    }
    
    public SSOAgentException(final Throwable cause) {
        super(cause);
    }
    
    public SSOAgentException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
