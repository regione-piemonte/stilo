/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgbrowser;

public abstract class AbstractUndoableCommand implements UndoableCommand
{
    protected String name;
    
    public void execute() {
    }
    
    public void undo() {
    }
    
    public void redo() {
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public boolean shouldExecute() {
        return true;
    }
}
