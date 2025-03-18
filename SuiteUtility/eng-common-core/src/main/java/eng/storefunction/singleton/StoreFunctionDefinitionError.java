/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction.singleton;

public class StoreFunctionDefinitionError extends Error
{
    public StoreFunctionDefinitionError(final Throwable cnfe) {
        super(cnfe);
    }
    
    public StoreFunctionDefinitionError(final String cnfe) {
        super(cnfe);
    }
}
