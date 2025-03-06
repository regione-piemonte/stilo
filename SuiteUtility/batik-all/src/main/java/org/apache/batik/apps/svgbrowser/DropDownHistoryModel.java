// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgbrowser;

import java.util.Locale;
import java.util.ArrayList;
import org.apache.batik.util.resources.ResourceManager;
import java.util.ResourceBundle;
import org.apache.batik.util.gui.DropDownComponent;

public class DropDownHistoryModel implements DropDownComponent.ScrollablePopupMenuModel
{
    private static final String RESOURCES = "org.apache.batik.apps.svgbrowser.resources.DropDownHistoryModelMessages";
    private static ResourceBundle bundle;
    private static ResourceManager resources;
    protected ArrayList items;
    protected HistoryBrowserInterface historyBrowserInterface;
    protected DropDownComponent.ScrollablePopupMenu parent;
    
    public DropDownHistoryModel(final DropDownComponent.ScrollablePopupMenu parent, final HistoryBrowserInterface historyBrowserInterface) {
        this.items = new ArrayList();
        this.parent = parent;
        this.historyBrowserInterface = historyBrowserInterface;
        historyBrowserInterface.getHistoryBrowser().addListener(new HistoryBrowser.HistoryBrowserAdapter() {
            public void historyReset(final HistoryBrowser.HistoryBrowserEvent historyBrowserEvent) {
                DropDownHistoryModel.this.clearAllScrollablePopupMenuItems("");
            }
        });
    }
    
    public String getFooterText() {
        return "";
    }
    
    public DropDownComponent.ScrollablePopupMenuItem createItem(final String s) {
        return new DropDownComponent.DefaultScrollablePopupMenuItem(this.parent, s);
    }
    
    protected void addItem(final DropDownComponent.ScrollablePopupMenuItem element, final String s) {
        final int size = this.items.size();
        this.items.add(0, element);
        this.parent.add(element, 0, size, this.items.size());
        this.parent.fireItemsWereAdded(new DropDownComponent.ScrollablePopupMenuEvent(this.parent, 1, 1, s));
    }
    
    protected void removeItem(final DropDownComponent.ScrollablePopupMenuItem o, final String s) {
        final int size = this.items.size();
        this.items.remove(o);
        this.parent.remove(o, size, this.items.size());
        this.parent.fireItemsWereRemoved(new DropDownComponent.ScrollablePopupMenuEvent(this.parent, 2, 1, s));
    }
    
    protected boolean removeLastScrollablePopupMenuItem(final String s) {
        final int index = this.items.size() - 1;
        if (index >= 0) {
            this.removeItem((DropDownComponent.ScrollablePopupMenuItem)this.items.get(index), s);
            return true;
        }
        return false;
    }
    
    protected boolean removeFirstScrollablePopupMenuItem(final String s) {
        final int index = 0;
        if (index < this.items.size()) {
            this.removeItem((DropDownComponent.ScrollablePopupMenuItem)this.items.get(index), s);
            return true;
        }
        return false;
    }
    
    protected void clearAllScrollablePopupMenuItems(final String s) {
        while (this.removeLastScrollablePopupMenuItem(s)) {}
    }
    
    public void processItemClicked() {
    }
    
    public void processBeforeShowed() {
        this.historyBrowserInterface.performCurrentCompoundCommand();
    }
    
    public void processAfterShowed() {
    }
    
    static {
        DropDownHistoryModel.bundle = ResourceBundle.getBundle("org.apache.batik.apps.svgbrowser.resources.DropDownHistoryModelMessages", Locale.getDefault());
        DropDownHistoryModel.resources = new ResourceManager(DropDownHistoryModel.bundle);
    }
    
    public static class RedoPopUpMenuModel extends DropDownHistoryModel
    {
        protected static String REDO_FOOTER_TEXT;
        protected static String REDO_TOOLTIP_PREFIX;
        
        public RedoPopUpMenuModel(final DropDownComponent.ScrollablePopupMenu scrollablePopupMenu, final HistoryBrowserInterface historyBrowserInterface) {
            super(scrollablePopupMenu, historyBrowserInterface);
            this.init();
        }
        
        private void init() {
            this.historyBrowserInterface.getHistoryBrowser().addListener(new HistoryBrowser.HistoryBrowserAdapter() {
                private final /* synthetic */ RedoPopUpMenuModel this$0 = this$0;
                
                public void executePerformed(final HistoryBrowser.HistoryBrowserEvent historyBrowserEvent) {
                    this.this$0.clearAllScrollablePopupMenuItems(RedoPopUpMenuModel.REDO_TOOLTIP_PREFIX + ((HistoryBrowser.CommandNamesInfo)historyBrowserEvent.getSource()).getLastRedoableCommandName());
                }
                
                public void undoPerformed(final HistoryBrowser.HistoryBrowserEvent historyBrowserEvent) {
                    final HistoryBrowser.CommandNamesInfo commandNamesInfo = (HistoryBrowser.CommandNamesInfo)historyBrowserEvent.getSource();
                    this.this$0.addItem(this.this$0.createItem(commandNamesInfo.getCommandName()), RedoPopUpMenuModel.REDO_TOOLTIP_PREFIX + commandNamesInfo.getLastRedoableCommandName());
                }
                
                public void redoPerformed(final HistoryBrowser.HistoryBrowserEvent historyBrowserEvent) {
                    this.this$0.removeFirstScrollablePopupMenuItem(RedoPopUpMenuModel.REDO_TOOLTIP_PREFIX + ((HistoryBrowser.CommandNamesInfo)historyBrowserEvent.getSource()).getLastRedoableCommandName());
                }
                
                public void doCompoundEdit(final HistoryBrowser.HistoryBrowserEvent historyBrowserEvent) {
                    if (this.this$0.parent.isEnabled()) {
                        this.this$0.parent.setEnabled(false);
                    }
                }
                
                public void compoundEditPerformed(final HistoryBrowser.HistoryBrowserEvent historyBrowserEvent) {
                }
            });
        }
        
        public String getFooterText() {
            return RedoPopUpMenuModel.REDO_FOOTER_TEXT;
        }
        
        public void processItemClicked() {
            this.historyBrowserInterface.getHistoryBrowser().compoundRedo(this.parent.getSelectedItemsCount());
        }
        
        static {
            RedoPopUpMenuModel.REDO_FOOTER_TEXT = DropDownHistoryModel.resources.getString("RedoModel.footerText");
            RedoPopUpMenuModel.REDO_TOOLTIP_PREFIX = DropDownHistoryModel.resources.getString("RedoModel.tooltipPrefix");
        }
    }
    
    public static class UndoPopUpMenuModel extends DropDownHistoryModel
    {
        protected static String UNDO_FOOTER_TEXT;
        protected static String UNDO_TOOLTIP_PREFIX;
        
        public UndoPopUpMenuModel(final DropDownComponent.ScrollablePopupMenu scrollablePopupMenu, final HistoryBrowserInterface historyBrowserInterface) {
            super(scrollablePopupMenu, historyBrowserInterface);
            this.init();
        }
        
        private void init() {
            this.historyBrowserInterface.getHistoryBrowser().addListener(new HistoryBrowser.HistoryBrowserAdapter() {
                private final /* synthetic */ UndoPopUpMenuModel this$0 = this$0;
                
                public void executePerformed(final HistoryBrowser.HistoryBrowserEvent historyBrowserEvent) {
                    final HistoryBrowser.CommandNamesInfo commandNamesInfo = (HistoryBrowser.CommandNamesInfo)historyBrowserEvent.getSource();
                    this.this$0.addItem(this.this$0.createItem(commandNamesInfo.getCommandName()), UndoPopUpMenuModel.UNDO_TOOLTIP_PREFIX + commandNamesInfo.getLastUndoableCommandName());
                }
                
                public void undoPerformed(final HistoryBrowser.HistoryBrowserEvent historyBrowserEvent) {
                    this.this$0.removeFirstScrollablePopupMenuItem(UndoPopUpMenuModel.UNDO_TOOLTIP_PREFIX + ((HistoryBrowser.CommandNamesInfo)historyBrowserEvent.getSource()).getLastUndoableCommandName());
                }
                
                public void redoPerformed(final HistoryBrowser.HistoryBrowserEvent historyBrowserEvent) {
                    final HistoryBrowser.CommandNamesInfo commandNamesInfo = (HistoryBrowser.CommandNamesInfo)historyBrowserEvent.getSource();
                    this.this$0.addItem(this.this$0.createItem(commandNamesInfo.getCommandName()), UndoPopUpMenuModel.UNDO_TOOLTIP_PREFIX + commandNamesInfo.getLastUndoableCommandName());
                }
                
                public void doCompoundEdit(final HistoryBrowser.HistoryBrowserEvent historyBrowserEvent) {
                    if (!this.this$0.parent.isEnabled()) {
                        this.this$0.parent.setEnabled(true);
                    }
                }
                
                public void compoundEditPerformed(final HistoryBrowser.HistoryBrowserEvent historyBrowserEvent) {
                }
            });
        }
        
        public String getFooterText() {
            return UndoPopUpMenuModel.UNDO_FOOTER_TEXT;
        }
        
        public void processItemClicked() {
            this.historyBrowserInterface.getHistoryBrowser().compoundUndo(this.parent.getSelectedItemsCount());
        }
        
        static {
            UndoPopUpMenuModel.UNDO_FOOTER_TEXT = DropDownHistoryModel.resources.getString("UndoModel.footerText");
            UndoPopUpMenuModel.UNDO_TOOLTIP_PREFIX = DropDownHistoryModel.resources.getString("UndoModel.tooltipPrefix");
        }
    }
}
