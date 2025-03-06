// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgbrowser;

import java.util.ArrayList;

public abstract class AbstractCompoundCommand extends AbstractUndoableCommand
{
    protected ArrayList atomCommands;
    
    public AbstractCompoundCommand() {
        this.atomCommands = new ArrayList();
    }
    
    public void addCommand(final UndoableCommand e) {
        if (e.shouldExecute()) {
            this.atomCommands.add(e);
        }
    }
    
    public void execute() {
        for (int size = this.atomCommands.size(), i = 0; i < size; ++i) {
            ((UndoableCommand)this.atomCommands.get(i)).execute();
        }
    }
    
    public void undo() {
        for (int i = this.atomCommands.size() - 1; i >= 0; --i) {
            ((UndoableCommand)this.atomCommands.get(i)).undo();
        }
    }
    
    public void redo() {
        for (int size = this.atomCommands.size(), i = 0; i < size; ++i) {
            ((UndoableCommand)this.atomCommands.get(i)).redo();
        }
    }
    
    public boolean shouldExecute() {
        boolean b = true;
        if (this.atomCommands.size() == 0) {
            b = false;
        }
        for (int size = this.atomCommands.size(), index = 0; index < size && b; b = (((UndoableCommand)this.atomCommands.get(index)).shouldExecute() && b), ++index) {}
        return b;
    }
    
    public int getCommandNumber() {
        return this.atomCommands.size();
    }
}
