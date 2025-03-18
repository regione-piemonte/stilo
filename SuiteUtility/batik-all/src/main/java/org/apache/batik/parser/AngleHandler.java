/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

public interface AngleHandler
{
    void startAngle() throws ParseException;
    
    void angleValue(final float p0) throws ParseException;
    
    void deg() throws ParseException;
    
    void grad() throws ParseException;
    
    void rad() throws ParseException;
    
    void endAngle() throws ParseException;
}
