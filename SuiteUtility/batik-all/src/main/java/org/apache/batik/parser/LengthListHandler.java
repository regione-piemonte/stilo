/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

public interface LengthListHandler extends LengthHandler
{
    void startLengthList() throws ParseException;
    
    void endLengthList() throws ParseException;
}
