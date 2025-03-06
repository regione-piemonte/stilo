// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgbrowser;

public interface UndoableCommand
{
    void execute();
    
    void undo();
    
    void redo();
    
    String getName();
    
    boolean shouldExecute();
}
