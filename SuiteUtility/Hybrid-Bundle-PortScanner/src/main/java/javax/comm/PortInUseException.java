/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package javax.comm;

public class PortInUseException extends Exception
{
    public String currentOwner;
    
    PortInUseException(final String s) {
        super("Port currently owned by " + s);
        this.currentOwner = s;
    }
}
