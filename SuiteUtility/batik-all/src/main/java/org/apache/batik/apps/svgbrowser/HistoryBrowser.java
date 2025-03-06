// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgbrowser;

import java.util.EventListener;
import java.util.EventObject;
import java.util.ArrayList;
import javax.swing.event.EventListenerList;

public class HistoryBrowser
{
    public static final int EXECUTING = 1;
    public static final int UNDOING = 2;
    public static final int REDOING = 3;
    public static final int IDLE = 4;
    protected EventListenerList eventListeners;
    protected ArrayList history;
    protected int currentCommandIndex;
    protected int historySize;
    protected int state;
    protected CommandController commandController;
    
    public HistoryBrowser(final CommandController commandController) {
        this.eventListeners = new EventListenerList();
        this.currentCommandIndex = -1;
        this.historySize = 1000;
        this.state = 4;
        this.history = new ArrayList();
        this.commandController = commandController;
    }
    
    public HistoryBrowser(final int historySize) {
        this.eventListeners = new EventListenerList();
        this.currentCommandIndex = -1;
        this.historySize = 1000;
        this.state = 4;
        this.history = new ArrayList();
        this.setHistorySize(historySize);
    }
    
    protected void setHistorySize(final int historySize) {
        this.historySize = historySize;
    }
    
    public void setCommandController(final CommandController commandController) {
        this.commandController = commandController;
    }
    
    public void addCommand(final UndoableCommand e) {
        for (int i = this.history.size() - 1; i > this.currentCommandIndex; --i) {
            this.history.remove(i);
        }
        if (this.commandController != null) {
            this.commandController.execute(e);
        }
        else {
            this.state = 1;
            e.execute();
            this.state = 4;
        }
        this.history.add(e);
        this.currentCommandIndex = this.history.size() - 1;
        if (this.currentCommandIndex >= this.historySize) {
            this.history.remove(0);
            --this.currentCommandIndex;
        }
        this.fireExecutePerformed(new HistoryBrowserEvent(new CommandNamesInfo(e.getName(), this.getLastUndoableCommandName(), this.getLastRedoableCommandName())));
    }
    
    public void undo() {
        if (this.history.isEmpty() || this.currentCommandIndex < 0) {
            return;
        }
        final UndoableCommand undoableCommand = this.history.get(this.currentCommandIndex);
        if (this.commandController != null) {
            this.commandController.undo(undoableCommand);
        }
        else {
            this.state = 2;
            undoableCommand.undo();
            this.state = 4;
        }
        --this.currentCommandIndex;
        this.fireUndoPerformed(new HistoryBrowserEvent(new CommandNamesInfo(undoableCommand.getName(), this.getLastUndoableCommandName(), this.getLastRedoableCommandName())));
    }
    
    public void redo() {
        if (this.history.isEmpty() || this.currentCommandIndex == this.history.size() - 1) {
            return;
        }
        final UndoableCommand undoableCommand = this.history.get(++this.currentCommandIndex);
        if (this.commandController != null) {
            this.commandController.redo(undoableCommand);
        }
        else {
            this.state = 3;
            undoableCommand.redo();
            this.state = 4;
        }
        this.fireRedoPerformed(new HistoryBrowserEvent(new CommandNamesInfo(undoableCommand.getName(), this.getLastUndoableCommandName(), this.getLastRedoableCommandName())));
    }
    
    public void compoundUndo(final int n) {
        for (int i = 0; i < n; ++i) {
            this.undo();
        }
    }
    
    public void compoundRedo(final int n) {
        for (int i = 0; i < n; ++i) {
            this.redo();
        }
    }
    
    public String getLastUndoableCommandName() {
        if (this.history.isEmpty() || this.currentCommandIndex < 0) {
            return "";
        }
        return this.history.get(this.currentCommandIndex).getName();
    }
    
    public String getLastRedoableCommandName() {
        if (this.history.isEmpty() || this.currentCommandIndex == this.history.size() - 1) {
            return "";
        }
        return this.history.get(this.currentCommandIndex + 1).getName();
    }
    
    public void resetHistory() {
        this.history.clear();
        this.currentCommandIndex = -1;
        this.fireHistoryReset(new HistoryBrowserEvent(new Object()));
    }
    
    public int getState() {
        if (this.commandController != null) {
            return this.commandController.getState();
        }
        return this.state;
    }
    
    public void addListener(final HistoryBrowserListener l) {
        this.eventListeners.add(HistoryBrowserListener.class, l);
    }
    
    public void fireExecutePerformed(final HistoryBrowserEvent historyBrowserEvent) {
        final Object[] listenerList = this.eventListeners.getListenerList();
        for (int length = listenerList.length, i = 0; i < length; i += 2) {
            if (listenerList[i] == HistoryBrowserListener.class) {
                ((HistoryBrowserListener)listenerList[i + 1]).executePerformed(historyBrowserEvent);
            }
        }
    }
    
    public void fireUndoPerformed(final HistoryBrowserEvent historyBrowserEvent) {
        final Object[] listenerList = this.eventListeners.getListenerList();
        for (int length = listenerList.length, i = 0; i < length; i += 2) {
            if (listenerList[i] == HistoryBrowserListener.class) {
                ((HistoryBrowserListener)listenerList[i + 1]).undoPerformed(historyBrowserEvent);
            }
        }
    }
    
    public void fireRedoPerformed(final HistoryBrowserEvent historyBrowserEvent) {
        final Object[] listenerList = this.eventListeners.getListenerList();
        for (int length = listenerList.length, i = 0; i < length; i += 2) {
            if (listenerList[i] == HistoryBrowserListener.class) {
                ((HistoryBrowserListener)listenerList[i + 1]).redoPerformed(historyBrowserEvent);
            }
        }
    }
    
    public void fireHistoryReset(final HistoryBrowserEvent historyBrowserEvent) {
        final Object[] listenerList = this.eventListeners.getListenerList();
        for (int length = listenerList.length, i = 0; i < length; i += 2) {
            if (listenerList[i] == HistoryBrowserListener.class) {
                ((HistoryBrowserListener)listenerList[i + 1]).historyReset(historyBrowserEvent);
            }
        }
    }
    
    public void fireDoCompoundEdit(final HistoryBrowserEvent historyBrowserEvent) {
        final Object[] listenerList = this.eventListeners.getListenerList();
        for (int length = listenerList.length, i = 0; i < length; i += 2) {
            if (listenerList[i] == HistoryBrowserListener.class) {
                ((HistoryBrowserListener)listenerList[i + 1]).doCompoundEdit(historyBrowserEvent);
            }
        }
    }
    
    public void fireCompoundEditPerformed(final HistoryBrowserEvent historyBrowserEvent) {
        final Object[] listenerList = this.eventListeners.getListenerList();
        for (int length = listenerList.length, i = 0; i < length; i += 2) {
            if (listenerList[i] == HistoryBrowserListener.class) {
                ((HistoryBrowserListener)listenerList[i + 1]).compoundEditPerformed(historyBrowserEvent);
            }
        }
    }
    
    public static class DocumentCommandController implements CommandController
    {
        protected DOMViewerController controller;
        protected int state;
        
        public DocumentCommandController(final DOMViewerController controller) {
            this.state = 4;
            this.controller = controller;
        }
        
        public void execute(final UndoableCommand undoableCommand) {
            this.controller.performUpdate(new Runnable() {
                private final /* synthetic */ DocumentCommandController this$0 = this$0;
                
                public void run() {
                    this.this$0.state = 1;
                    undoableCommand.execute();
                    this.this$0.state = 4;
                }
            });
        }
        
        public void undo(final UndoableCommand undoableCommand) {
            this.controller.performUpdate(new Runnable() {
                private final /* synthetic */ DocumentCommandController this$0 = this$0;
                
                public void run() {
                    this.this$0.state = 2;
                    undoableCommand.undo();
                    this.this$0.state = 4;
                }
            });
        }
        
        public void redo(final UndoableCommand undoableCommand) {
            this.controller.performUpdate(new Runnable() {
                private final /* synthetic */ DocumentCommandController this$0 = this$0;
                
                public void run() {
                    this.this$0.state = 3;
                    undoableCommand.redo();
                    this.this$0.state = 4;
                }
            });
        }
        
        public int getState() {
            return this.state;
        }
    }
    
    public interface CommandController
    {
        void execute(final UndoableCommand p0);
        
        void undo(final UndoableCommand p0);
        
        void redo(final UndoableCommand p0);
        
        int getState();
    }
    
    public static class CommandNamesInfo
    {
        private String lastUndoableCommandName;
        private String lastRedoableCommandName;
        private String commandName;
        
        public CommandNamesInfo(final String commandName, final String lastUndoableCommandName, final String lastRedoableCommandName) {
            this.lastUndoableCommandName = lastUndoableCommandName;
            this.lastRedoableCommandName = lastRedoableCommandName;
            this.commandName = commandName;
        }
        
        public String getLastRedoableCommandName() {
            return this.lastRedoableCommandName;
        }
        
        public String getLastUndoableCommandName() {
            return this.lastUndoableCommandName;
        }
        
        public String getCommandName() {
            return this.commandName;
        }
    }
    
    public static class HistoryBrowserAdapter implements HistoryBrowserListener
    {
        public void executePerformed(final HistoryBrowserEvent historyBrowserEvent) {
        }
        
        public void undoPerformed(final HistoryBrowserEvent historyBrowserEvent) {
        }
        
        public void redoPerformed(final HistoryBrowserEvent historyBrowserEvent) {
        }
        
        public void historyReset(final HistoryBrowserEvent historyBrowserEvent) {
        }
        
        public void compoundEditPerformed(final HistoryBrowserEvent historyBrowserEvent) {
        }
        
        public void doCompoundEdit(final HistoryBrowserEvent historyBrowserEvent) {
        }
    }
    
    public static class HistoryBrowserEvent extends EventObject
    {
        public HistoryBrowserEvent(final Object source) {
            super(source);
        }
    }
    
    public interface HistoryBrowserListener extends EventListener
    {
        void executePerformed(final HistoryBrowserEvent p0);
        
        void undoPerformed(final HistoryBrowserEvent p0);
        
        void redoPerformed(final HistoryBrowserEvent p0);
        
        void historyReset(final HistoryBrowserEvent p0);
        
        void doCompoundEdit(final HistoryBrowserEvent p0);
        
        void compoundEditPerformed(final HistoryBrowserEvent p0);
    }
}
