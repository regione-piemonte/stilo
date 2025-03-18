/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.script;

import java.net.URL;

public interface InterpreterFactory
{
    String[] getMimeTypes();
    
    Interpreter createInterpreter(final URL p0, final boolean p1);
}
