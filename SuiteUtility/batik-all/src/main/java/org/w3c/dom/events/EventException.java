/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.w3c.dom.events;

public class EventException extends RuntimeException
{
    public short code;
    public static final short UNSPECIFIED_EVENT_TYPE_ERR = 0;
    public static final short DISPATCH_REQUEST_ERR = 1;
    
    public EventException(final short code, final String message) {
        super(message);
        this.code = code;
    }
}
