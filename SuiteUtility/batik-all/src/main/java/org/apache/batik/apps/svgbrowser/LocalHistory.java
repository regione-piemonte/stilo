// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgbrowser;

import java.awt.event.ActionEvent;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.AbstractButton;
import javax.swing.JMenuItem;
import java.util.ArrayList;
import javax.swing.JMenuBar;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import java.util.List;
import javax.swing.JMenu;

public class LocalHistory
{
    protected JSVGViewerFrame svgFrame;
    protected JMenu menu;
    protected int index;
    protected List visitedURIs;
    protected int currentURI;
    protected ButtonGroup group;
    protected ActionListener actionListener;
    protected int state;
    protected static final int STABLE_STATE = 0;
    protected static final int BACK_PENDING_STATE = 1;
    protected static final int FORWARD_PENDING_STATE = 2;
    protected static final int RELOAD_PENDING_STATE = 3;
    
    public LocalHistory(final JMenuBar menuBar, final JSVGViewerFrame svgFrame) {
        this.visitedURIs = new ArrayList();
        this.currentURI = -1;
        this.group = new ButtonGroup();
        this.actionListener = new RadioListener();
        this.svgFrame = svgFrame;
        for (int menuCount = menuBar.getMenuCount(), i = 0; i < menuCount; ++i) {
            final JMenu menu = menuBar.getMenu(i);
            for (int itemCount = menu.getItemCount(), j = 0; j < itemCount; ++j) {
                final JMenuItem item = menu.getItem(j);
                if (item != null && "@@@".equals(item.getText())) {
                    (this.menu = menu).remove(this.index = j);
                    return;
                }
            }
        }
        throw new IllegalArgumentException("No '@@@' marker found");
    }
    
    public void back() {
        this.update();
        this.state = 1;
        this.currentURI -= 2;
        this.svgFrame.showSVGDocument(this.visitedURIs.get(this.currentURI + 1));
    }
    
    public boolean canGoBack() {
        return this.currentURI > 0;
    }
    
    public void forward() {
        this.update();
        this.state = 2;
        this.svgFrame.showSVGDocument(this.visitedURIs.get(this.currentURI + 1));
    }
    
    public boolean canGoForward() {
        return this.currentURI < this.visitedURIs.size() - 1;
    }
    
    public void reload() {
        this.update();
        this.state = 3;
        --this.currentURI;
        this.svgFrame.showSVGDocument(this.visitedURIs.get(this.currentURI + 1));
    }
    
    public void update(final String actionCommand) {
        if (this.currentURI < -1) {
            throw new IllegalStateException("Unexpected currentURI:" + this.currentURI);
        }
        this.state = 0;
        if (++this.currentURI < this.visitedURIs.size()) {
            if (!this.visitedURIs.get(this.currentURI).equals(actionCommand)) {
                for (int i = this.menu.getItemCount() - 1; i >= this.index + this.currentURI + 1; --i) {
                    this.group.remove(this.menu.getItem(i));
                    this.menu.remove(i);
                }
                this.visitedURIs = this.visitedURIs.subList(0, this.currentURI + 1);
            }
            this.group.remove(this.menu.getItem(this.index + this.currentURI));
            this.menu.remove(this.index + this.currentURI);
            this.visitedURIs.set(this.currentURI, actionCommand);
        }
        else {
            if (this.visitedURIs.size() >= 15) {
                this.visitedURIs.remove(0);
                this.group.remove(this.menu.getItem(this.index));
                this.menu.remove(this.index);
                --this.currentURI;
            }
            this.visitedURIs.add(actionCommand);
        }
        String substring = actionCommand;
        int n = actionCommand.lastIndexOf(47);
        if (n == -1) {
            n = actionCommand.lastIndexOf(92);
        }
        if (n != -1) {
            substring = actionCommand.substring(n + 1);
        }
        final JRadioButtonMenuItem radioButtonMenuItem = new JRadioButtonMenuItem(substring);
        radioButtonMenuItem.setToolTipText(actionCommand);
        radioButtonMenuItem.setActionCommand(actionCommand);
        radioButtonMenuItem.addActionListener(this.actionListener);
        this.group.add(radioButtonMenuItem);
        radioButtonMenuItem.setSelected(true);
        this.menu.insert(radioButtonMenuItem, this.index + this.currentURI);
    }
    
    protected void update() {
        switch (this.state) {
            case 1: {
                this.currentURI += 2;
                break;
            }
            case 3: {
                ++this.currentURI;
                break;
            }
        }
    }
    
    protected class RadioListener implements ActionListener
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            final String actionCommand = actionEvent.getActionCommand();
            LocalHistory.this.currentURI = this.getItemIndex((JMenuItem)actionEvent.getSource()) - 1;
            LocalHistory.this.svgFrame.showSVGDocument(actionCommand);
        }
        
        public int getItemIndex(final JMenuItem menuItem) {
            for (int itemCount = LocalHistory.this.menu.getItemCount(), i = LocalHistory.this.index; i < itemCount; ++i) {
                if (LocalHistory.this.menu.getItem(i) == menuItem) {
                    return i - LocalHistory.this.index;
                }
            }
            throw new IllegalArgumentException("MenuItem is not from my menu!");
        }
    }
}
